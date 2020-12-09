package ru.ecom.ejb.services.file;

/**
 * Получение пути к файлу, записанному на стороне JBoss'a
 */
public interface IJbossGetFileService {
    /** Получение относительного пути к файлу */
    String getRelativeFilename(long aFileId) ;

    /** Получение абсолютного  пути к файлу */
    String getAbsoluteFilename(long aFileId) ;

    /** Регистрация нового файла */
    long register() ;
}
