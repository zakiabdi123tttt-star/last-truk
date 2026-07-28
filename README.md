# Truck Material Management System — Backend (Spring Boot)

## Isku xirka Database-ka

Backend-kani wuxuu ku xiran yahay database-ka aad horay u dhistay (`truck-database` package-ga):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/truck_material_management_system
spring.datasource.username=postgres
spring.datasource.password=123
```

⚠️ Hubi database-ka (`docker-compose up -d`) inuu **socdo** ka hor inta aadan backend-ka bilaabin.

## Sida loo run gareeyo

1. Fur `backend/` folder-ka IntelliJ IDEA (ama IDE kale oo Java ah)
2. Hubi in **JDK 17** la isticmaalayo (Lombok kuma shaqeeyo JDK-yada aad ugu cusub)
3. Maven wuu soo dejin doonaa dependencies-ka si otomaatig ah
4. Run garee:

```
cd backend
mvn spring-boot:run
```

Backend-ku wuxuu ku shaqeyn doonaa: `http://localhost:8080`

## Admin default-ka

- Username: `admin`
- Password: `admin123`

(Kani waa admin-ka app-ka gudihiisa, kama midka database connection-ka — ha isku qasin labadan.)

## Endpoint-yada

| Method | Endpoint | Faahfaahin |
|---|---|---|
| POST | `/api/auth/login` | Soo celiya JWT token |
| GET/POST/PUT/DELETE | `/api/trucks` | Trucks Module |
| GET/POST/PUT/DELETE | `/api/materials` | Materials (Admin ayaa maamula) |
| GET/POST/PUT/DELETE | `/api/customers` | + `/{id}/summary` (payment history, balance) |
| GET/POST/PUT/DELETE | `/api/trips` | + query params `start`/`end` (ISO date) |
| GET/POST/DELETE | `/api/expenses` | |
| GET/POST | `/api/payments` | + `/trip/{tripId}` |
| GET | `/api/dashboard` | Total Income/Expense/Profit, chart data |

Dhammaan endpoint-yada (marka laga reebo `/api/auth/**`) waxay u baahan yihiin:

```
Authorization: Bearer <token>
```

## Waxa Xigaya

Frontend-ka (React + Tailwind) weli lama bilaabin — sheeg marka aad diyaar tahay.

---

## Deploy-gareynta Production-ka (Render)

Backend-kani hadda waa diyaar loo geli production — wuxuu u akhriyaa xogta database-ka iyo JWT secret-ka iyada oo **environment variables** ah, ma aha kuwo hardcoded ah.

### 1. Ku dar Dockerfile-ka repo-kaaga
Dockerfile-ka (`backend/Dockerfile`) horeba wuu ku jira package-kan — Render wuxuu si otomaatig ah u ogaan doonaa inuu Docker isticmaalo.

### 2. Push GitHub
```
git init
git add .
git commit -m "Truck Management backend"
git remote add origin <URL-ka repo-kaaga>
git push -u origin main
```

### 3. Abuur Database-ka Render
1. Fur **render.com** → **New +** → **PostgreSQL**
2. Magac u dhig (tusaale `truckms-db`), dooro **Free** tier
3. Marka la abuuro, ka koobiye **Internal Database URL** (ama External haddii aad rabto inaad pgAdmin ka gasho)

### 4. Run garee schema-ga database-ka cusub
Isticmaal **External Database URL** (Render wuxuu ku siinayaa host/port/user/password/database gaar ah) — ku xir pgAdmin sida aad database-ka localhost ugu xirnayd, kadibna ku shub `init.sql` (database package-kaaga).

### 5. Abuur Web Service-ka Backend-ka
1. **New +** → **Web Service** → dooro repo-gaaga GitHub
2. **Root Directory**: `backend` (haddii repo-gu leeyahay frontend + backend labadaba)
3. **Environment**: Render wuxuu ogaan doonaa Dockerfile-ka si otomaatig ah
4. Ku dar **Environment Variables**:

| Key | Value |
|---|---|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://<host-ka-render>:5432/<db-name>` |
| `SPRING_DATASOURCE_USERNAME` | (ka koobiye Render dashboard) |
| `SPRING_DATASOURCE_PASSWORD` | (ka koobiye Render dashboard) |
| `JWT_SECRET` | qoraal random ah oo dheer (tusaale 40+ xaraf) |
| `CORS_ALLOWED_ORIGINS` | `https://yourprojectname.vercel.app` (frontend-kaaga Vercel) |

5. Guji **Create Web Service** — Render wuxuu build-gareyn doonaa Docker image-ka oo deploy-gareyn doona
6. Marka uu dhammaado, waxaad heli doontaa URL sida: `https://truckms-backend.onrender.com`

### 6. Beddel frontend-ka si uu ula xiriiro backend-ka cusub
Frontend package-ka gudihiisa, beddel `.env`:
```
VITE_API_URL=https://truckms-backend.onrender.com/api
```
Kadib dib u deploy-gareyso Vercel (ama ku dar environment variable-ka isla magaca Vercel dashboard-ka gudihiisa, halkii aad `.env` file-ka isu bedeli lahayd).

### Fiiro gaar ah: Render Free Tier
Free tier-ka Render wuxuu **seexdaa** (spin down) marka aan la isticmaalin ~15 daqiiqo — request-ka kowaad ee ku xiga wuxuu qaadan karaa 30-60 ilbiriqsi (cold start). Tani waa caadi, mana aha khalad.

