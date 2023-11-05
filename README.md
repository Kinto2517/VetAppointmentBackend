<a name="readme-top"></a>

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
    </li>
    <li><a href="#built-with">Usage</a></li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>


## About The Project

The VetAppointment Backend is the server-side component of a comprehensive system designed to streamline and enhance the management of veterinary appointments. This backend serves as the core of the VetAppointment ecosystem, offering the necessary functionality to support various features within the application.

Developed with efficiency and user-friendliness in mind, the VetAppointment Backend handles the logic and data management for services such as appointment scheduling, client and pet information, vet doctor profiles, educational backgrounds, and professional experience. It ensures secure and reliable communication with the front-end and provides a robust foundation for building a seamless veterinary appointment management system.

This backend is implemented using modern web technologies, including Java, Spring Boot, and various other libraries to ensure reliability, security, and scalability. It adheres to best practices for API design, authentication, and authorization, making it a solid choice for building a powerful and user-friendly veterinary appointment management solution.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Getting Started

### Prerequisites

Before you begin, make sure you have the following prerequisites:

* Docker installed on your system.
* Docker Compose installed.

### Installation Steps

#### Clone the Repository:

Start by cloning the VetAppointment Backend repository to your local machine.

```
git clone [https://github.com/yourusername/vetappointment-backend.git](https://github.com/Kinto2517/VetAppointmentBackend.git)
```

#### Set Up Docker Compose File:

Create a docker-compose.yml file in the root directory where you cloned the repository.
```
version: "3"
services:
  zookeeper:
    image: wurstmeister/zookeeper
    ports:
      - "2181:2181"
  kafka:
    image: wurstmeister/kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 101
      KAFKA_ADVERTISED_HOST_NAME: localhost
      KAFKA_ADVERTISED_PORT: 9092
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
    depends_on:
      - zookeeper
```

#### Starting The Project

Start the project with your favorite IDE with Docker running.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Built With

* ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
* ![PostgreSql](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
* ![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-000?style=for-the-badge&logo=apachekafka)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



## Usage

* ![Screenshot_1](https://github.com/Kinto2517/VetAppointmentBackend/assets/54002766/c2037ce3-10cb-4e30-a5bd-8e125be65618)

### Vet Doctor Controller

PUT /api/v1/vetdoctors/{id}: Update a VetDoctor's profile including City, Description, Phone Number, Name.
PUT /api/v1/vetdoctors/{id}/update-specializations: Update a VetDoctor's specializations. This allows doctor to add pre-created specialization.
PUT /api/v1/vetdoctors/{id}/update-experience/{experienceId}: Update a VetDoctor's experience that already created.
PUT /api/v1/vetdoctors/{id}/update-education/{educationId}: Update a VetDoctor's education that already created.
PUT /api/v1/vetdoctors/{id}/add-profile-picture: Add or update a VetDoctor's profile picture. You can select "png", "jpg" or "jpeg"
POST /api/v1/vetdoctors/{id}/add-experience: Add a new experience to a VetDoctor's profile.
POST /api/v1/vetdoctors/{id}/add-education: Add a new education to a VetDoctor's profile.
PATCH /api/v1/vetdoctors/change-password: Change the password for a VetDoctor. Spring security handle the authentication backend and makes sure that you change only your own password.
GET /api/v1/vetdoctors/{id}/specializations: Retrieve a VetDoctor's specializations.

- These APIs for clients to see the doctors.
GET /api/v1/vetdoctors/{id}/experiences: Retrieve a VetDoctor's experience records.
GET /api/v1/vetdoctors/{id}/educations: Retrieve a VetDoctor's education records.
GET /api/v1/vetdoctors/all: Retrieve information about all VetDoctors.

DELETE /api/v1/vetdoctors/{id}/delete-experience/{experienceId}: Delete a specific experience record for a VetDoctor.
DELETE /api/v1/vetdoctors/{id}/delete-education/{educationId}: Delete a specific education record for a VetDoctor.


![Screenshot_2](https://github.com/Kinto2517/VetAppointmentBackend/assets/54002766/bc93ac92-3351-44e5-b576-3170430d4bb7)

![Screenshot_3](https://github.com/Kinto2517/VetAppointmentBackend/assets/54002766/dd103329-c6ef-4248-874c-93dda168199f)

![Screenshot_4](https://github.com/Kinto2517/VetAppointmentBackend/assets/54002766/ff34c712-1146-42c7-bfa1-436b348efa15)

![Screenshot_5](https://github.com/Kinto2517/VetAppointmentBackend/assets/54002766/ca7e8f73-52c6-48d9-a586-6bc3ccc48868)


<p align="right">(<a href="#readme-top">back to top</a>)</p>



## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>


## Contact

Ersin Yılmaz ASLAN - [@My LinkedIn]([https://twitter.com/your_username](https://tr.linkedin.com/in/ersinya))

Project Link: [Vet Appointment Backend]([https://github.com/your_username/repo_name](https://github.com/Kinto2517/VetAppointmentBackend))

<p align="right">(<a href="#readme-top">back to top</a>)</p>
