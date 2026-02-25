# 🌿 Viktorifit Backend

The core backend service for the Viktorifit ecosystem — handling all communication between the frontend and machine learning service, including auth, FAQ, ML integration, feedback, exercises, history, and more.

## ✨ Features

### 🎯 **Core Functionality**

- **Authentication**: JWT-based login & registration with email verification via Brevo
- **ML Integration**: Proxies requests from the frontend to the Viktorifit ML service
- **Exercises & History**: Tracks user workout activity and progress logs
- **Feedback & FAQ**: Manages user feedback and frequently asked questions
- **RESTful API**: Clean API structure served under `/api/v1.0`

### 🛠️ **Tech Stack**

- **Framework**: Spring Boot
- **Database**: MySQL
- **Auth**: JWT
- **Email**: Brevo (via API for production) and Brevo (via stmp for development)
- **Containerization**: Docker

---

## 🚀 Getting Started (Local)

### Prerequisites

- [Docker](https://www.docker.com/get-started) & Docker Compose installed
- Git

### 1. Clone the repository

```bash
git clone https://github.com/crisvito/viktorifit-backend.git
cd viktorifit-backend
```

### 2. Switch `application.properties` and `email-service` to local config

Open `src/main/resources/application.properties` and **comment out the production block**, then **uncomment the local block** at the top of the file so it looks like this:

```properties
spring.application.name=viktorifit

# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/viktorifit
spring.datasource.username=root
spring.datasource.password=viktoria03
server.servlet.context-path=/api/v1.0
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Email Configuration
spring.mail.host=smtp-relay.brevo.com
spring.mail.port=587
spring.mail.username=${BREVO_SMTP_USERNAME}
spring.mail.password=${BREVO_SMTP_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.protocol=smtp
spring.mail.properties.mail.smtp.from=${BREVO_MAIL_FROM}

app.jwt.secret=${JWT_SECRET}
app.jwt.expiration=3600000

ml.api.base-url=http://localhost:8000

app.base.url=http://localhost:8080/api/v1.0

# production
**Comment the line below**
```

Open `src/main/java/com/viktoria/viktorifit/utilty/email/service/EmailService.java` and **comment out the production block**, then **uncomment the local block** at the top of the file so it looks like this:

```properties
// Development
package com.viktoria.viktorifit.utility.email.service;
import java.util.List;
import java.util.Map;
.
.
.
@Service
public class EmailService {
    ....
}
# production
**Comment the line below**
```

### 3. Buat file `.env` di root project

```dotenv
BREVO_SMTP_USERNAME=your_brevo_smtp_username
BREVO_SMTP_PASSWORD=your_brevo_smtp_password
BREVO_MAIL_FROM=your@email.com

JWT_SECRET=your_jwt_secret_here
```

### 4. Build Docker

```bash
docker build -t viktorifit-api .
```

### 5. Jalankan

```bash
docker run -p 8080:8080 viktorifit-api
```

API akan tersedia di [http://localhost:8080/api/v1.0](http://localhost:8080/api/v1.0) ✅

> **Catatan:** Pastikan juga ML service sudah berjalan di `localhost:8000` jika ingin menggunakan fitur ML.

---

## 🚀 Deployment (Railway)

Open `src/main/resources/application.properties` and **comment out the development block**, then **uncomment the production block** at the top of the file so it looks like this:

```properties
# production
spring.application.name=viktorifit

spring.datasource.url=jdbc:mysql://${MYSQLHOST}:${MYSQLPORT}/${MYSQLDATABASE}
spring.datasource.username=${MYSQLUSER}
spring.datasource.password=${MYSQLPASSWORD}
server.servlet.context-path=/api/v1.0
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

brevo.api.key=${BREVO_API_KEY}
app.mail.from=crisvitoc@gmail.com

app.jwt.secret=${JWT_SECRET}
app.jwt.expiration=3600000

ml.api.base-url=${ML_API_URL}

app.base.url=${BACKEND_URL}/api/v1.0
app.frontend.url=${FRONTEND_URL}
```

Open `src/main/java/com/viktoria/viktorifit/utilty/email/service/EmailService.java` and **comment out the production block**, then **uncomment the local block** at the top of the file so it looks like this:

```properties
// Production
package com.viktoria.viktorifit.utility.email.service;
import org.springframework.beans.factory.annotation.Value;
.
.
.
@Service
@RequiredArgsConstructor
public class EmailService {
    ...
}
```

### 1. Push ke GitHub

Pastikan kode sudah di-push ke GitHub repository kamu.

### 2. Deploy ke Railway

1. Buka [railway.app](https://railway.app) dan buat project baru
2. Pilih **"Deploy from GitHub repo"** → pilih repo ini
3. Tambahkan plugin **MySQL** di Railway (klik **+ New** → **Database** → **MySQL**)
4. Railway akan otomatis mengisi variabel `MYSQLHOST`, `MYSQLPORT`, `MYSQLDATABASE`, `MYSQLUSER`, `MYSQLPASSWORD`

### 3. Set Environment Variables

Pergi ke tab **Variables** pada service backend, lalu tambahkan variabel berikut:

| Variable        | Keterangan                                                                                |
| --------------- | ----------------------------------------------------------------------------------------- |
| `BREVO_API_KEY` | Your API Key from BREVO                                                                   |
| `JWT_SECRET`    | Secret key for JWT token                                                                  |
| `ML_API_URL`    | URL Railway internal Viktorifit ML (contoh: `http://viktorifit-ml.railway.internal:8000`) |
| `BACKEND_URL`   | URL Railway Viktorifit Backend (contoh: `https://viktorifit-backend.up.railway.app`)      |
| `FRONTEND_URL`  | URL dari frontend app (contoh: `https://viktorifit.vercel.app`)                           |
| `MYSQLHOST`     | mysql.railway.internal                                                                    |
| `MYSQLPORT`     | 3306 (your port in railway mysql)                                                         |
| `MYSQLDATABASE` | railway                                                                                   |
| `MYSQLUSER`     | root                                                                                      |
| `MYSQLPASSWORD` | Get From MYSQLPASSWORD in Railway (from Railway SQl added before)                         |

> Variabel MySQL (`MYSQLHOST`, `MYSQLPORT`, dll.) sudah otomatis terisi oleh Railway saat plugin MySQL ditambahkan — tidak perlu diisi manual.

> Pastikan Deploy terlebih dahulu ML service

### 4. Deploy

Railway akan otomatis build dan deploy. Selesai! 🎉

---

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

**Made with ❤️ by Viktorifit Team**

_The backbone of your fitness journey._ 🌿✨
