package dk.nine.demo.view;

public interface BaseMapper<I, T extends BaseResource<I>, E extends BasePersistentItem<I>> {

    T toDto(E persistentItem);

    E toEntity(T resource);

    void updateFromResource(T resource, E persistentItem);
}
