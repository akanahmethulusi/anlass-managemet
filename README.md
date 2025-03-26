
1️⃣ Projektbeschreibung
Anlass-Management ist eine Vaadin + Spring Boot + PostgreSQL-Anwendung zur Verwaltung von Veranstaltungen.
Mit dieser Anwendung können Benutzer:

Anlässe erstellen, bearbeiten und löschen
Dateien hochladen & herunterladen
Fragen, Gruppen und Kategorien verwalten
Such- und Filterfunktionen nutzen
Benutzeranmeldungen verwalten
2️⃣ Technologie-Stack
✅ Backend: Spring Boot, Spring Data JPA, PostgreSQL
✅ Frontend: Vaadin
✅ Datenbank: PostgreSQL (mit Docker)
✅ Build-Tool: Maven
✅ Authentifizierung: (Falls geplant: JWT oder OAuth)

3️⃣ Installation & Setup
🔹 Voraussetzungen
Java 17+
Maven
Docker (für PostgreSQL)
🔹 Projekt klonen & starten

git clone https://github.com/dein-repo/anlass-management.git
cd anlass-management
mvn clean install
docker-compose up -d  # Startet die PostgreSQL-Datenbank
mvn spring-boot:run   # Startet das Backend


4️⃣ API-Endpunkte (Falls REST-API genutzt wird)
Methode	Endpunkt	Beschreibung
GET	/api/anlaesse	Alle Anlässe abrufen
POST	/api/anlaesse	Neuen Anlass erstellen
PUT	/api/anlaesse/{id}	Anlass aktualisieren
DELETE	/api/anlaesse/{id}	Anlass löschen
POST	/api/anlaesse/upload/{id}	Datei hochladen
GET	/api/anlaesse/download/{id}	Datei herunterladen
5️⃣ Features
📌 Anlässe verwalten: Erstellen, Bearbeiten, Löschen
🔍 Suchfunktion: Filtern nach Titeln
📂 Datei-Upload & Download
📅 Verwaltung von Gruppen & Kategorien
🔐 Benutzerverwaltung (Falls geplant)
6️⃣ Screenshots (Falls gewünscht)
Du kannst Bilder der UI hinzufügen, z. B.:
📸 docs/screenshots/dashboard.png

7️⃣ Verbesserungen & TODOs
Falls geplant, kann eine Liste mit zukünftigen Features hinzugefügt werden:

 Benutzer-Authentifizierung hinzufügen
 PDF-Export der Anlässe ermöglichen
 E-Mail-Benachrichtigung für Anmeldungen
