package com.kinto2517.vetappointmentbackend.service.impl;

import com.kinto2517.vetappointmentbackend.controller.VetDoctorController;
import com.kinto2517.vetappointmentbackend.dto.*;
import com.kinto2517.vetappointmentbackend.entity.*;
import com.kinto2517.vetappointmentbackend.kafka.NotificationProducer;
import com.kinto2517.vetappointmentbackend.repository.*;
import com.kinto2517.vetappointmentbackend.service.AppointmentConferenceService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentConferenceServiceImpl implements AppointmentConferenceService {

    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;
    private final VetDoctorRepository vetDoctorRepository;
    private final VideoConferenceRepository videoConferenceRepository;
    private final AvailabilityRepository availabilityRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationProducer notificationProducer;
    private static final Logger logger = LoggerFactory.getLogger(VetDoctorController.class);

    @Autowired
    public AppointmentConferenceServiceImpl(AppointmentRepository appointmentRepository,
                                            ClientRepository clientRepository,
                                            VetDoctorRepository vetDoctorRepository,
                                            VideoConferenceRepository videoConferenceRepository,
                                            AvailabilityRepository availabilityRepository, NotificationRepository notificationRepository, NotificationProducer notificationProducer) {
        this.appointmentRepository = appointmentRepository;
        this.clientRepository = clientRepository;
        this.vetDoctorRepository = vetDoctorRepository;
        this.videoConferenceRepository = videoConferenceRepository;
        this.availabilityRepository = availabilityRepository;
        this.notificationRepository = notificationRepository;
        this.notificationProducer = notificationProducer;
    }


    @Override
    @Transactional
    public List<AppointmentDTO> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        List<AppointmentDTO> appointmentDTOs = appointments.stream()
                .map(appointment -> new AppointmentDTO(appointment.getId(),
                        appointment.getStartTime(),
                        appointment.getEndTime(),
                        appointment.getVideoConference().getMeetingId(),
                        appointment.getVetDoctor().getFullName(),
                        appointment.getClient().getFirstName(),
                        appointment.getClient().getLastName()))
                .collect(Collectors.toList());

        return appointmentDTOs;
    }

    @Override
    public Void deleteAppointment(Long id) {
        Optional<Appointment> existingAppointment = appointmentRepository.findById(id);

        if (existingAppointment.isPresent()) {
            logger.info("Deleting appointment with ID " + id);
            appointmentRepository.delete(existingAppointment.get());
            return null;
        } else {
            throw new EntityNotFoundException("Appointment with ID " + id + " not found");
        }

    }

    @Override
    @Transactional
    public List<AppointmentDTO> getClientAppointments(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow();
        List<Appointment> appointments = appointmentRepository.findByClient(client);

        List<AppointmentDTO> appointmentDTOs = appointments.stream()
                .map(appointment -> new AppointmentDTO(appointment.getId(),
                        appointment.getStartTime(),
                        appointment.getEndTime(),
                        appointment.getVideoConference().getMeetingId(),
                        appointment.getVetDoctor().getFullName(),
                        appointment.getClient().getFirstName(),
                        appointment.getClient().getLastName()))
                .collect(Collectors.toList());
        logger.info("Getting appointments for client with name " + client.getFirstName() + " " + client.getLastName());
        logger.info("Getting appointments for client with ID " + client.getId());
        return appointmentDTOs;
    }

    @Override
    @Transactional
    public List<AppointmentDTO> getVetDoctorAppointments(Long vetDoctorId) {
        VetDoctor vetDoctor = vetDoctorRepository.findById(vetDoctorId).orElseThrow();
        List<Appointment> appointments = appointmentRepository.findByVetDoctor(vetDoctor);

        List<AppointmentDTO> appointmentDTOs = appointments.stream()
                .map(appointment -> new AppointmentDTO(appointment.getId(),
                        appointment.getStartTime(),
                        appointment.getEndTime(),
                        appointment.getVideoConference().getMeetingId(),
                        appointment.getVetDoctor().getFullName(),
                        appointment.getClient().getFirstName(),
                        appointment.getClient().getLastName()))
                .collect(Collectors.toList());
        logger.info("Getting appointments for vet doctor with name " + vetDoctor.getFullName());
        logger.info("Getting appointments for vet doctor with ID " + vetDoctor.getId());

        return appointmentDTOs;
    }

    @Override
    @Transactional
    public AppointmentDTO createAppointment(Long clientId, Long vetDoctorId, AppointmentConferenceSaveRequest appointmentConferenceSaveRequest) {

        Client client = clientRepository.findById(clientId).orElseThrow();
        VetDoctor vetDoctor = vetDoctorRepository.findById(vetDoctorId).orElseThrow();

        List<Availability> availabilities = availabilityRepository.findByVetDoctor(vetDoctor);

        for (Availability availability : availabilities) {
            logger.info("Availability with start time " + availability.getStartTime() + " and end time " + availability.getEndTime());
            logger.info("Appointment with start time " + appointmentConferenceSaveRequest.startTime() + " and end time " + appointmentConferenceSaveRequest.endTime());
            if (availability.getStartTime().equals(appointmentConferenceSaveRequest.startTime())
                    && availability.getEndTime().equals(appointmentConferenceSaveRequest.endTime())) {
                if (availability.isBooked()) {
                    logger.info("Availability with ID " + availability.getId() + " is already booked");
                    throw new EntityNotFoundException("Availability with ID " + availability.getId() + " is already booked");
                } else {
                    Appointment appointment = new Appointment();
                    appointment.setClient(client);
                    appointment.setVetDoctor(vetDoctor);
                    appointment.setStartTime(appointmentConferenceSaveRequest.startTime());
                    appointment.setEndTime(appointmentConferenceSaveRequest.endTime());

                    VideoConference videoConference = new VideoConference();
                    videoConference.setTitle("[Company] Zoom Call With " + vetDoctor.getFullName() + " at " + appointmentConferenceSaveRequest.startTime());
                    videoConference.setMeetingId(appointmentConferenceSaveRequest.meetingId());

                    appointment.setVideoConference(videoConference);
                    videoConference.setAppointment(appointment);

                    appointmentRepository.save(appointment);
                    videoConferenceRepository.save(videoConference);

                    availability.setBooked(true);
                    availabilityRepository.save(availability);

                    sendNotificationToClient(client, vetDoctor);

                    sendNotificationToVetDoctor(vetDoctor, client);

                    logger.info("Creating appointment for client with name " + client.getFirstName() + " " + client.getLastName());
                    logger.info("Creating appointment for vet doctor with name " + vetDoctor.getFullName());

                    return new AppointmentDTO(appointment.getId(), appointment.getStartTime(), appointment.getEndTime(), appointment.getVideoConference().getMeetingId(), appointment.getVetDoctor().getFullName(), appointment.getClient().getFirstName(), appointment.getClient().getLastName());
                }
            } else {
                logger.info("Availability with start time " + appointmentConferenceSaveRequest.startTime() +
                        " is not the same with " + availability.getStartTime() + " and end time " + appointmentConferenceSaveRequest.endTime() +
                        " is not the same with " + availability.getEndTime());
            }

        }
        throw new EntityNotFoundException("Availability with start time " + appointmentConferenceSaveRequest.startTime() + " and end time " + appointmentConferenceSaveRequest.endTime() + " not found");
    }

    private void sendNotificationToClient(Client client, VetDoctor vetDoctor) {
        Notification notification = new Notification();
        String message = "You have a new appointment with " + vetDoctor.getFullName() + " at " + new Date();
        notification.setMessage(message);
        notification.setSentDate(new Date());
        notification.setClient(client);
        notification.setVetDoctor(null);
        notificationRepository.save(notification);
        logger.info("Sending notification to client: " + client.getFirstName() + " " + client.getLastName());
    }

    private void sendNotificationToVetDoctor(VetDoctor vetDoctor, Client client) {
        Notification notification = new Notification();
        String message = "You have a new appointment with " + client.getFirstName() + " " + client.getLastName() + " at " + new Date();
        notification.setMessage(message);
        notification.setSentDate(new Date());
        notification.setVetDoctor(vetDoctor);
        notification.setClient(null);
        notificationRepository.save(notification);
        logger.info("Sending notification to vet doctor: " + vetDoctor.getFullName());
    }

    @Override
    @Transactional
    public AvailabilityDTO createAvailability(Long vetDoctorId, AvailabilitySaveRequest availabilitySaveRequest) {
        VetDoctor vetDoctor = vetDoctorRepository.findById(vetDoctorId).orElseThrow();

        Availability availability = new Availability();
        availability.setStartTime(availabilitySaveRequest.startTime());
        availability.setEndTime(availabilitySaveRequest.endTime());
        availability.setBooked(false);
        availability.setVetDoctor(vetDoctor);
        availabilityRepository.save(availability);
        vetDoctor.getAvailabilities().add(availability);

        logger.info("Creating availability for vet doctor with name " + vetDoctor.getFullName());

        return new AvailabilityDTO(
                availability.getId(),
                availability.getStartTime(),
                availability.getEndTime(),
                availability.isBooked(),
                availability.getVetDoctor().getFullName());
    }

    @Override
    @Transactional
    public List<AvailabilityDTO> getVetDoctorAvailabilities(Long vetDoctorId) {
        VetDoctor vetDoctor = vetDoctorRepository.findById(vetDoctorId).orElseThrow();
        List<Availability> availabilities = availabilityRepository.findByVetDoctor(vetDoctor);

        List<AvailabilityDTO> availabilityDTOs = availabilities.stream()
                .map(availability -> new AvailabilityDTO(
                        availability.getId(),
                        availability.getStartTime(),
                        availability.getEndTime(),
                        availability.isBooked(),
                        availability.getVetDoctor().getFullName()))
                .collect(Collectors.toList());

        return availabilityDTOs;
    }

    @Override
    @Transactional
    public Void deleteAvailability(Long id) {
        Optional<Availability> existingAvailability = availabilityRepository.findById(id);

        if (existingAvailability.isPresent()) {
            logger.info("Deleting availability with ID " + id);
            availabilityRepository.delete(existingAvailability.get());
            return null;
        } else {
            throw new EntityNotFoundException("Availability with ID " + id + " not found");
        }
    }

    @Override
    @Transactional
    public List<NotificationDTO> getClientNotifications(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow();
        List<Notification> notifications = notificationRepository.findByClientAndVetDoctorNull(client);

        if (notifications.isEmpty()) {
            throw new EntityNotFoundException("Notifications for client with ID " + clientId + " not found");
        }
        List<NotificationDTO> notificationDTOs = notifications.stream()
                .map(notification -> new NotificationDTO(
                        notification.getId(),
                        notification.getMessage()
                ))
                .collect(Collectors.toList());
        logger.info("Getting notifications for client with name " + client.getFirstName() + " " + client.getLastName());
        return notificationDTOs;
    }

    @Override
    @Transactional
    public List<NotificationDTO> getVetDoctorNotifications(Long vetDoctorId) {
        VetDoctor vetDoctor = vetDoctorRepository.findById(vetDoctorId).orElseThrow();
        List<Notification> notifications = notificationRepository.findByVetDoctorAndClientNull(vetDoctor);
        if (notifications.isEmpty()) {
            throw new EntityNotFoundException("Notifications for vet doctor with ID " + vetDoctorId + " not found");
        }
        List<NotificationDTO> notificationDTOs = notifications.stream()
                .map(notification -> new NotificationDTO(
                        notification.getId(),
                        notification.getMessage()
                ))
                .collect(Collectors.toList());
        logger.info("Getting notifications for vet doctor with name " + vetDoctor.getFullName());
        return notificationDTOs;
    }

    @Override
    public Void deleteVetDoctorNotification(Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow();
        logger.info("Deleting notification with ID " + id);
        notificationRepository.delete(notification);
        return null;
    }

    @Override
    public Void deleteClientNotification(Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow();
        logger.info("Deleting notification with ID " + id);
        notificationRepository.delete(notification);
        return null;
    }
}
