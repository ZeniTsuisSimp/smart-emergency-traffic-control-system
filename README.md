# Smart Emergency Traffic Control System

This project implements a Smart Emergency Traffic Control System using Spring Boot for the backend.

## Architecture

### Backend (Spring Boot)
The backend exposes REST APIs to track the ambulance location and control the traffic signal status.

- **Controller**: `TrafficController` handles HTTP requests.
- **Service**: `TrafficService` contains the logic to calculate the distance between the ambulance and the traffic light.
- **Model**: `Location` (latitude, longitude) and `SignalStatus` (RED, GREEN, YELLOW).

### API Endpoints

1.  **Update Ambulance Location**
    -   **Method**: `POST`
    -   **URL**: `/api/ambulance/location`
    -   **Body**:
        ```json
        {
          "latitude": 12.9716,
          "longitude": 77.5946
        }
        ```
    -   **Description**: Receives the live location of the ambulance. The backend calculates the distance to the traffic light. If the distance is less than 300 meters, the signal status is set to GREEN.

2.  **Get Signal Status**
    -   **Method**: `GET`
    -   **URL**: `/api/signal/status`
    -   **Response**: `"RED"` or `"GREEN"`
    -   **Description**: Returns the current status of the traffic light.

## Frontend (Angular - Conceptual)

To complete the system, you will need an Angular frontend with two main components:

1.  **Ambulance Tracker**:
    -   Uses the browser's Geolocation API or Google Maps API to get the current latitude and longitude.
    -   Periodically sends this data to the backend using the `POST /api/ambulance/location` endpoint.

2.  **Traffic Signal Display**:
    -   Periodically polls the `GET /api/signal/status` endpoint.
    -   Updates the UI to show a Red or Green light based on the response.

## Running the Backend

1.  Run the `Main` class in `src/main/java/org/SmartEmergencyTrafficControl/Main.java`.
2.  The server will start on `http://localhost:8080`.
