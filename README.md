# VIKTORIFIT Backend — Intelligent Fitness Server (Spring Boot)

**VIKTORIFIT Backend** adalah server utama dalam arsitektur **Client–Server** yang menangani logika bisnis, manajemen data, dan inferensi Machine Learning untuk platform kebugaran VIKTORIFIT.

Backend ini dibangun menggunakan **Spring Boot 3.x** dan berfungsi sebagai **REST API server** yang melayani aplikasi Frontend (Angular client).

Sistem dirancang untuk mendukung personalisasi workout, meal planning, dan analisis progres pengguna secara real-time dengan performa tinggi.

---

## Client–Server Architecture

VIKTORIFIT menggunakan arsitektur **Client–Server** yang memisahkan tanggung jawab sistem:

### 🖥️ Client (Frontend)

* Menyediakan antarmuka pengguna (UI/UX)
* Mengelola interaksi pengguna
* Mengirim permintaan API ke server

### 🧠 Server (Backend — repositori ini)

* Menjalankan logika bisnis utama
* Mengelola data pengguna
* Melakukan inferensi Machine Learning
* Menyediakan RESTful API
* Menjamin keamanan dan autentikasi

```
Frontend Client (Angular)
        ↓ REST API
VIKTORIFIT Backend Server (Spring Boot + ML Engine)
        ↓
Data & ML Models
```

---

## Architecture Overview

Backend menggunakan pendekatan **Modular Monolith Architecture** untuk menjaga skalabilitas dan maintainability.

### Core Modules (`app` package)

* **config**
  Konfigurasi sistem dan bean definitions.

* **exercise**
  Domain logic katalog latihan fisik.

* **ml**
  Bridge untuk inferensi model Machine Learning.

* **security**
  Implementasi Spring Security dan autentikasi.

* **user**
  Manajemen profil dan progres pengguna.

* **inquiry & faq**
  Sistem bantuan dan feedback pengguna.

* **utility**
  Helper classes untuk pemrosesan data.

---

## 📂 Project Structure

```bash
viktorifit-backend/
├── app/          # Source code backend
├── data/         # Dataset
├── models/       # Model ML (.pickle / .onnx)
├── notebooks/    # Riset & eksperimen
├── src/
└── pom.xml
```

---

## Data Pipeline & Integration Workflow

### Research & Modeling

Eksperimen dilakukan di `notebooks/` menggunakan Python.

### Model Deployment

Model disimpan di `models/` dalam format:

* `.pickle`
* `.onnx`

### Data Ingestion

Backend membaca dataset dari `data/` dan melakukan inferensi untuk menghasilkan respons API dinamis.

---

## Security & Performance

* **Stateless authentication** dengan Spring Security
* Validasi konfigurasi melalui metadata
* Pemisahan dataset & model untuk skalabilitas

---

## Getting Started

### Prerequisites

* Java 17+
* Maven

### Installation

```bash
git clone https://github.com/username/viktorifit-backend.git
cd viktorifit-backend
```

Pastikan folder berikut tersedia:

```
data/
models/
```

### Build & Run

```bash
./mvnw clean install
./mvnw spring-boot:run
```

Server berjalan di:

```
http://localhost:8080
```

---

## API Role

Backend berfungsi sebagai:

* REST API provider
* Machine Learning inference engine
* User data management system
* Security & authentication layer

---

## Production Deployment

Untuk produksi:

* Gunakan environment variables
* Aktifkan production profile
* Deploy via Docker atau cloud server

---

## 🤝 Contribution

1. Fork repository
2. Buat branch fitur
3. Commit perubahan
4. Submit pull request

