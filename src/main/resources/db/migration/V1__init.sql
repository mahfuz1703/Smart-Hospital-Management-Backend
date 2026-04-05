-- Enable uuid generation support (requires pgcrypto)
CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE roles (
  id uuid PRIMARY KEY,
  name varchar(32) NOT NULL,
  CONSTRAINT uk_roles_name UNIQUE (name)
);

CREATE TABLE users (
  id uuid PRIMARY KEY,
  email varchar(254) NOT NULL,
  password_hash varchar(100) NOT NULL,
  enabled boolean NOT NULL DEFAULT true,
  created_at timestamptz NOT NULL DEFAULT now(),
  updated_at timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT uk_users_email UNIQUE (email)
);

CREATE TABLE user_roles (
  user_id uuid NOT NULL,
  role_id uuid NOT NULL,
  CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id),
  CONSTRAINT uk_user_roles_user_role UNIQUE (user_id, role_id)
);

CREATE TABLE patients (
  id uuid PRIMARY KEY,
  user_id uuid NOT NULL,
  full_name varchar(120) NOT NULL,
  date_of_birth date,
  phone varchar(30),
  address varchar(255),
  created_at timestamptz NOT NULL DEFAULT now(),
  updated_at timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT fk_patients_user FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT uk_patients_user_id UNIQUE (user_id)
);

CREATE TABLE doctors (
  id uuid PRIMARY KEY,
  user_id uuid NOT NULL,
  full_name varchar(120) NOT NULL,
  specialization varchar(80) NOT NULL,
  license_number varchar(50),
  years_of_experience int NOT NULL DEFAULT 0,
  created_at timestamptz NOT NULL DEFAULT now(),
  updated_at timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT fk_doctors_user FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT uk_doctors_user_id UNIQUE (user_id),
  CONSTRAINT uk_doctors_license_number UNIQUE (license_number)
);

CREATE TABLE doctor_availability_slots (
  id uuid PRIMARY KEY,
  doctor_id uuid NOT NULL,
  start_time timestamptz NOT NULL,
  end_time timestamptz NOT NULL,
  status varchar(16) NOT NULL,
  created_at timestamptz NOT NULL DEFAULT now(),
  updated_at timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT fk_slots_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);

CREATE INDEX idx_slots_doctor_start ON doctor_availability_slots(doctor_id, start_time);

CREATE TABLE appointments (
  id uuid PRIMARY KEY,
  doctor_id uuid NOT NULL,
  patient_id uuid NOT NULL,
  availability_slot_id uuid NOT NULL,
  status varchar(16) NOT NULL,
  reason varchar(500),
  created_at timestamptz NOT NULL DEFAULT now(),
  updated_at timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT fk_appointments_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
  CONSTRAINT fk_appointments_patient FOREIGN KEY (patient_id) REFERENCES patients(id),
  CONSTRAINT fk_appointments_slot FOREIGN KEY (availability_slot_id) REFERENCES doctor_availability_slots(id),
  CONSTRAINT uk_appointments_slot UNIQUE (availability_slot_id)
);

CREATE INDEX idx_appointments_doctor ON appointments(doctor_id);
CREATE INDEX idx_appointments_patient ON appointments(patient_id);

CREATE TABLE medical_records (
  id uuid PRIMARY KEY,
  patient_id uuid NOT NULL,
  doctor_id uuid NOT NULL,
  appointment_id uuid,
  diagnosis text,
  prescription text,
  notes text,
  created_at timestamptz NOT NULL DEFAULT now(),
  updated_at timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT fk_medical_records_patient FOREIGN KEY (patient_id) REFERENCES patients(id),
  CONSTRAINT fk_medical_records_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
  CONSTRAINT fk_medical_records_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);

CREATE INDEX idx_medical_records_patient_created ON medical_records(patient_id, created_at);