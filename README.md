

---

# Task Decomposition Service

A Spring Boot microservice that leverages OpenAI’s GPT models to break down engineering prompts into detailed, step-by-step subtasks. This service acts as an intelligent assistant to enhance task planning and project management.

---

## Features

* Integrates with OpenAI Chat Completion API (`gpt-3.5-turbo` or `gpt-4`) for natural language processing.
* Converts high-level engineering prompts into structured subtasks.
* Exposes REST API for easy integration with other services or UI clients.
* Lightweight, containerized Spring Boot application for cloud-native deployments.
* Configurable via environment variables for flexibility and security.
* Includes robust error handling and logging for maintainability.

---

## Tech Stack

* **Language:** Java 21
* **Framework:** Spring Boot 3.2.5
* **API Client:** Spring WebFlux WebClient
* **JSON Processing:** Jackson
* **Build:** Maven
* **Containerization:** Docker (using Eclipse Temurin JRE Alpine image)
* **External API:** OpenAI Chat Completion API (`https://api.openai.com/v1/chat/completions`)

---

## Prerequisites

* Java 21 JDK installed locally (for local development)
* Maven 3.6+ for build and dependency management
* Docker (optional, for containerized deployment)
* OpenAI API key with sufficient quota and permissions

---

## Setup and Configuration

1. Clone the repository:

   ```bash
   git clone https://github.com/your-org/task-decomposition.git
   cd task-decomposition
   ```

2. Add your OpenAI API key to `src/main/resources/application.properties`:

   ```properties
   openai.api.key=YOUR_OPENAI_API_KEY_HERE
   ```

   **Or** provide it via environment variable for production use:

   ```bash
   export OPENAI_API_KEY=YOUR_OPENAI_API_KEY_HERE
   ```

3. Build the project with Maven:

   ```bash
   mvn clean package
   ```

---

## Running the Application

### Locally with Maven

```bash
mvn spring-boot:run
```

Application starts on port `3001` by default.

### Using Docker

Build the Docker image:

```bash
docker build -t task-decomposition .
```

Run the Docker container:

```bash
docker run -p 3001:3001 -e OPENAI_API_KEY=$OPENAI_API_KEY task-decomposition
```

---

## API Usage

### Endpoint: `POST /api/decompose`

**Request Body:**

```json
{
  "prompt": "Design a robotic arm for assembly line"
}
```

**Response:**

```json
{
  "root": {
    "subtasks": [
      "Define requirements for robotic arm",
      "Select appropriate materials",
      "Design mechanical structure",
      "... more subtasks ..."
    ]
  }
}
```

---

## Logging

* Application logs detailed API request/response information.
* Errors from OpenAI API or internal failures are logged with stack traces for easy troubleshooting.

---

## Troubleshooting & Common Issues

* **429 Too Many Requests:** Exceeded OpenAI API quota; check your usage and plan.
* **Model Not Found (404):** Verify model name and OpenAI subscription.
* **Port conflicts:** Ensure port `3001` is free or update `application.properties` with a different port.

---

## Future Enhancements

* Add authentication and rate limiting for API endpoints.
* Support dynamic model selection (e.g., switch between GPT-3.5 and GPT-4).
* Implement asynchronous processing with queues for long prompts.
* Add UI client integration for real-time task decomposition.

---

## Author

Chandra Sekhar Gupta — Software Engineer
Email: \[[your.email@example.com](mailto:your.email@example.com)]
LinkedIn: \[linkedin.com/in/your-profile]

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---


