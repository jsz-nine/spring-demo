package dk.nine.demo.view;

public interface BaseMapper<I, T extends DtoResource<I>, E extends ModelResource <I>> {

    T toDto(E modelResource);

    E toModel(T dtoResource);

    void updateFromResource(T dtoResource, E modelResource);
}
