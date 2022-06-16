package fmi.java.web.tourFinder.util;


import fmi.java.web.tourFinder.model.TourPicture;
import fmi.java.web.tourFinder.model.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class CommonUtils {
    public static User getLoggedUser(HttpServletRequest httpServletRequest) {
        return (User) httpServletRequest.getAttribute(Constants.LOGGED_USER);
    }

    public static <T> boolean DoesEnumContain(Class<? extends Enum<?>> clazz, T value) {
        return isValueContained(getEnumNames(clazz), value);
    }

    public static <T> boolean isValueContained(T[] values, T value) {
        for (T val : values) {
            if (val.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String[] getEnumNames(Class<? extends Enum<?>> clazz) {
        return Arrays.stream(clazz.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }

    public static String encodeBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static <T> Collection<T> asCollection(Iterable<T> iterable) {
        Collection<T> collection = new ArrayList<>();
        for (T t : iterable) {
            collection.add(t);
        }
        return collection;
    }

    public static <T> Set<T> asSet(Collection<T> collection) {
        return new HashSet<>(collection);
    }

    public static <T> Set<T> addItemToSet(Set<T> currentSet, T item) {
        Set<T> newSet = new HashSet<>(currentSet);
        newSet.add(item);
        return newSet;
    }

    public static <T> Set<T> removeItemFromSet(Set<T> currentSet, T itemToRemove) {
        Set<T> newSet = new HashSet<>(currentSet);
        newSet.remove(itemToRemove);
        return newSet;
    }

    public static String convertFromMultipartFileToBase64(MultipartFile picture) {
        try {
            byte[] bytes = picture.getBytes();
            return encodeBase64(bytes);
        } catch (Exception ex) {
            //TODO: handle exception
        }
        return null;
    }

    public static List<TourPicture> convertFromMultipartToTourPicture(List<MultipartFile> pictures) {
        List<TourPicture> tourPictures = new ArrayList<>();
        for (MultipartFile picture : pictures) {
            tourPictures.add(new TourPicture(convertFromMultipartFileToBase64(picture)));
        }
        return tourPictures;
    }
}