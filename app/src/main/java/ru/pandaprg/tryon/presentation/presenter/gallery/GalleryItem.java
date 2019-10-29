/**
 * Элемент набора для хранения галерей
 *  поле    imageName   хранит имя изображения
 *  поле    imagePath   хранит путь к файлу
 */
package ru.pandaprg.tryon.presentation.presenter.gallery;

public class GalleryItem {
    private String imageName;
    private String imagePath;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public GalleryItem(String imageName, String imagePath) {
        this.imageName = imageName;
        this.imagePath = imagePath;
    }
}
