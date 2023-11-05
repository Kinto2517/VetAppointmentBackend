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

Docker installed on your system.
Docker Compose installed.

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

![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSql](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-000?style=for-the-badge&logo=apachekafka)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



## Usage

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.

_For more examples, please refer to the [Documentation](https://example.com)_

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
