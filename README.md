# GIOS Data Scraper

## Opis

GIOS Data Scraper to projekt napisany w Javie, który korzysta z API GIOS (Główny Inspektorat Ochrony Środowiska) do pobierania danych o stacjach i instalacjach monitorujących jakość powietrza.

## Funkcje

- Pobieranie listy wszystkich stacji z API GIOS.
- Pobieranie listy instalacji dla każdej stacji.
- Obsługa błędów i ponawianie żądań w przypadku niepowodzenia.

## Wymagania

- Java 8 lub nowsza
- Maven

## Jak uruchomić

1. Sklonuj repozytorium na swoje lokalne środowisko.
2. Przejdź do katalogu projektu.
3. Uruchom `mvn clean install` aby zbudować projekt.
4. Uruchom projekt za pomocą `java -jar target/gios-data-scraper-1.0-SNAPSHOT.jar`.

## Struktura plików

- `Main`: Główna klasa aplikacji, która uruchamia proces pobierania danych z API GIOS.
- `Station`: Klasa reprezentująca stację monitorującą jakość powietrza, zawierająca informacje takie jak nazwa, lokalizacja i lista instalacji.
- `Installation`: Klasa reprezentująca instalację monitorującą jakość powietrza, zawierająca szczegółowe informacje o danej instalacji.
- `APIHandler`: Klasa odpowiedzialna za komunikację z API GIOS, pobieranie danych i obsługę błędów.

## Autor

Krzysztof Caban
