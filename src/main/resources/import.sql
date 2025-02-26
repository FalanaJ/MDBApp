INSERT INTO patients (first_name, last_name, date_of_birth, gender, pesel_number, phone_number, email, address) VALUES ('Jan', 'Kowalski', '1985-03-15', 'MALE', '85031512345', '123456789', 'jan.kowalski@example.com', 'ul. Długa 5, Warszawa');
INSERT INTO patients (first_name, last_name, date_of_birth, gender, pesel_number, phone_number, email, address) VALUES ('Anna', 'Nowak', '1992-07-22', 'FEMALE', '92072267890', '987654321', 'anna.nowak@example.com', 'ul. Krótka 12, Kraków');
INSERT INTO patients (first_name, last_name, date_of_birth, gender, pesel_number, phone_number, email, address) VALUES ('Piotr', 'Wiśniewski', '1978-11-05', 'MALE', '78110534567', '564738291', 'piotr.wisniewski@example.com', 'ul. Szeroka 8, Gdańsk');
INSERT INTO patients (first_name, last_name, date_of_birth, gender, pesel_number, phone_number, email, address) VALUES ('Ewa', 'Zielińska', '1995-06-30', 'FEMALE', '95063045678', '112233445', 'ewa.zielinska@example.com', 'ul. Miodowa 3, Poznań');
INSERT INTO patients (first_name, last_name, date_of_birth, gender, pesel_number, phone_number, email, address) VALUES ('Tomasz', 'Nowicki', '1980-01-12', 'MALE', '80011298765', '667788990', 'tomasz.nowicki@example.com', 'ul. Wiślana 10, Wrocław');
INSERT INTO patients (first_name, last_name, date_of_birth, gender, pesel_number, phone_number, email, address) VALUES ('Marta', 'Kaczmarek', '1990-02-25', 'FEMALE', '90022545678', '223344556', 'marta.kaczmarek@example.com', 'ul. Zielona 15, Łódź');
INSERT INTO patients (first_name, last_name, date_of_birth, gender, pesel_number, phone_number, email, address) VALUES ('Marcin', 'Lewandowski', '1982-09-18', 'MALE', '82091887654', '556677889', 'marcin.lewandowski@example.com', 'ul. Wrocławska 20, Poznań');
INSERT INTO patients (first_name, last_name, date_of_birth, gender, pesel_number, phone_number, email, address) VALUES ('Katarzyna', 'Kamińska', '1995-04-14', 'FEMALE', '95041433456', '998877665', 'katarzyna.kaminska@example.com', 'ul. Lipowa 7, Kraków');
INSERT INTO patients (first_name, last_name, date_of_birth, gender, pesel_number, phone_number, email, address) VALUES ('Adam', 'Szymański', '1976-08-03', 'MALE', '76080365789', '445566778', 'adam.szymanski@example.com', 'ul. Polna 9, Warszawa');

INSERT INTO doctors (first_name, last_name, speciality, phone_number, email, address) VALUES ('Jan', 'Motyl', 'Kardiolog', '123456789', 'jan.motyl@example.com', 'ul. Przykładowa 10, 00-001 Warszawa');
INSERT INTO doctors (first_name, last_name, speciality, phone_number, email, address) VALUES ('Anna', 'Nowak', 'Pediatra', '987654321', 'anna.nowak@example.com', 'ul. Przykładowa 20, 00-002 Warszawa');
INSERT INTO doctors (first_name, last_name, speciality, phone_number, email, address) VALUES ('Marek', 'Zielinski', 'Chirurg', '456123789', 'marek.zielinski@example.com', 'ul. Przykładowa 30, 00-003 Warszawa');

INSERT INTO medical_history (doctor_id, doctor_Last_Name, date_of_appointment, reason, status, created_at, patient_id) VALUES (1, 'Motyl', '2025-02-01', 'Routine checkup', 'COMPLETED', '2025-02-01', 1);
INSERT INTO medical_history (doctor_id, doctor_Last_Name, date_of_appointment, reason, status, created_at, patient_id) VALUES (1, 'Motyl', '2025-02-05', 'Flu symptoms', 'SCHEDULED', '2025-02-05', 2);
INSERT INTO medical_history (doctor_id, doctor_Last_Name, date_of_appointment, reason, status, created_at, patient_id) VALUES (2, 'Nowak', '2025-02-10', 'Back pain', 'CANCELED', '2025-02-10', 3);
INSERT INTO medical_history (doctor_id, doctor_Last_Name, date_of_appointment, reason, status, created_at, patient_id) VALUES (3, 'Zielinski', '2025-02-12', 'Headache', 'COMPLETED', '2025-02-12', 4);
INSERT INTO medical_history (doctor_id, doctor_Last_Name, date_of_appointment, reason, status, created_at, patient_id) VALUES (1, 'Motyl', '2025-02-15', 'Blood pressure check', 'SCHEDULED', '2025-02-15', 5);
INSERT INTO medical_history (doctor_id, doctor_Last_Name, date_of_appointment, reason, status, created_at, patient_id) VALUES (2, 'Nowak', '2025-02-18', 'Dental checkup', 'COMPLETED', '2025-02-18', 6);
INSERT INTO medical_history (doctor_id, doctor_Last_Name, date_of_appointment, reason, status, created_at, patient_id) VALUES (3, 'Zielinski', '2025-02-20', 'Skin rash', 'SCHEDULED', '2025-02-20', 7);
INSERT INTO medical_history (doctor_id, doctor_Last_Name, date_of_appointment, reason, status, created_at, patient_id) VALUES (1, 'Motyl', '2025-02-22', 'Routine checkup', 'CANCELED', '2025-02-22', 8);
INSERT INTO medical_history (doctor_id, doctor_Last_Name, date_of_appointment, reason, status, created_at, patient_id) VALUES (2, 'Nowak', '2025-02-25', 'Sore throat', 'COMPLETED', '2025-02-25', 9);
INSERT INTO medical_history (doctor_id, doctor_Last_Name, date_of_appointment, reason, status, created_at, patient_id) VALUES (3, 'Zielinski', '2025-03-01', 'Annual checkup', 'COMPLETED', '2025-03-01', 1);
INSERT INTO medical_history (doctor_id, doctor_Last_Name, date_of_appointment, reason, status, created_at, patient_id) VALUES (1, 'Motyl', '2025-03-03', 'Chest pain', 'SCHEDULED', '2025-03-03', 2);
INSERT INTO medical_history (doctor_id, doctor_Last_Name, date_of_appointment, reason, status, created_at, patient_id) VALUES (2, 'Nowak', '2025-03-05', 'Fever', 'CANCELED', '2025-03-05', 3);
INSERT INTO medical_history (doctor_id, doctor_Last_Name, date_of_appointment, reason, status, created_at, patient_id) VALUES (3, 'Zielinski', '2025-03-07', 'Eye irritation', 'COMPLETED', '2025-03-07', 4);
INSERT INTO medical_history (doctor_id, doctor_Last_Name, date_of_appointment, reason, status, created_at, patient_id) VALUES (1, 'Motyl', '2025-03-10', 'Vaccination', 'SCHEDULED', '2025-03-10', 5);
INSERT INTO medical_history (doctor_id, doctor_Last_Name, date_of_appointment, reason, status, created_at, patient_id) VALUES (2, 'Nowak', '2025-03-12', 'Headache', 'CANCELED', '2025-03-12', 6);
INSERT INTO medical_history (doctor_id, doctor_Last_Name, date_of_appointment, reason, status, created_at, patient_id) VALUES (3, 'Zielinski', '2025-03-15', 'Blood test', 'COMPLETED', '2025-03-15', 7);
INSERT INTO medical_history (doctor_id, doctor_Last_Name, date_of_appointment, reason, status, created_at, patient_id) VALUES (1, 'Motyl', '2025-03-18', 'Flu symptoms', 'SCHEDULED', '2025-03-18', 8);
INSERT INTO medical_history (doctor_id, doctor_Last_Name, date_of_appointment, reason, status, created_at, patient_id) VALUES (2, 'Nowak', '2025-03-20', 'Allergy checkup', 'COMPLETED', '2025-03-20', 9);




