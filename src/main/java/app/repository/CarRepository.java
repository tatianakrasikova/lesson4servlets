package app.repository;

import app.model.Car;

import java.util.List;



public interface CarRepository {
    // Получение всех машин
    List<Car> getAll();
    //Получаем машину по id
    Car getById(long id);

    // Сохранение машины в Хранилище Данных
    Car save(Car car);

    // Обновление существующего автомобиля
    Car update(Car car);

    // Удаление по id
    void deleteById(long id);
}
