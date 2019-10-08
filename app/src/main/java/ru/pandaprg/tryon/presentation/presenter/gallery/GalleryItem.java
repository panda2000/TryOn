/**
 * Элемент набора для хранения галерей
 *  поле    imageName   хранит имя изображения
 *  поле    imagePath   хранит путь к файлу
 */
package ru.pandaprg.tryon.presentation.presenter.gallery;

public class GalleryItem {
    private String imageName;
    private int imagePath;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getImagePath() {
        return imagePath;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }

    public GalleryItem(String imageName, int imagePath) {
        this.imageName = imageName;
        this.imagePath = imagePath;
    }
}
