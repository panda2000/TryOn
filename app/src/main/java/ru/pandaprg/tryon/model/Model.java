/*
 *  Класс хранения имени файла. В приложении файл мы получаеам в окне Gallery, а отображаем в окне Video
 *  реализован синглтоном, для доступа из любой точки приложения
 */
package ru.pandaprg.tryon.model;

public class Model {
    private static Model model;

    String pictureName;

    private Model () {}

    public static Model getInstance () {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }
}
