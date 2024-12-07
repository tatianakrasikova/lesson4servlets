package app.repository;

import app.constants.Constants;
import app.model.Car;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static app.constants.Constants.*;




public class CarRepositoryDB implements CarRepository {


    private Connection getConnection() {

        try {
            // Подгрузили класс драйвера Docker в память приложения
            Class.forName(DB_DRIVER_PATH);

            // jdbc:postgres://localhost:5433/g_49_cars?user=my_user&password=pos1234
            String dbUrl = String.format("%s%s?user=%s&password=%s",
                    DB_ADDRESS, DB_NAME, DB_USER, DB_PASSWORD);

            return DriverManager.getConnection(dbUrl);


        } catch (Exception e ) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Car> getAll() {
        try (Connection connection = getConnection()) {


        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return List.of();
    }

    @Override
    public Car getById(long id) {
        // SELECT * FROM cars WHERE id = 5;

        try (Connection connection = getConnection()) {

            String query = String.format("SELECT * FROM cars WHERE id = %d", id);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                Long idDb = resultSet.getLong("id");
                String brand = resultSet.getString("brand");
                BigDecimal price = resultSet.getBigDecimal("price");
                int year = resultSet.getInt("year");

                Car car = new Car(brand, price, year);
                car.setId(idDb);
                return car;
            }

            // В ResultSet ничего нет
            return null;



        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public Car save(Car car) {
        try (Connection connection = getConnection()) {
           /*
           1. Составить sql-запрос
            */

            // INSERT INTO cars (brand, price, year) VALUES ('Toyota', 35000, 2022);
            String query = String.format("INSERT INTO cars (brand, price, year) VALUES ('%s', %s, %d);",
                    car.getBrand(), car.getPrice(), car.getYear());

            Statement statement = connection.createStatement();

            // statement.execute(query); - запросы, который вносят  изменения в БД (POST, PUT..)
            // statement.executeQuery(query); - запросы для получения данных (GET)

            // Т.к. у нас метод save вносит изменения (записывает авто в БД) - мы используем execute().
            statement.execute(query, Statement.RETURN_GENERATED_KEYS);

            // ResultSet - объект, который используется для хранения данных, полученных в результате
            // выполнения SQL-запроса
            // Табличное представление данных - каждая строка соответствует записи в БД. Столбец - поле этой записи.
            ResultSet resultSet = statement.getGeneratedKeys();

            resultSet.next();
           /*
           getString(String columnLabel) - вернет значение указанного столбца как строку
           getInt(String columnLabel) - вернет значение указанного столбца как число
           getDate(String columnLabel) - вернет значение указанного столбца как объект Date
            */
            // Получаю значение столбца id как Long
            Long id = resultSet.getLong("id");
            // Long id = resultSet.getLong(1);
            car.setId(id);

            return car;

        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public Car update(Car car) {
        try (Connection connection = getConnection()) {


        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void deleteById(long id) {
        try (Connection connection = getConnection()) {


        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
