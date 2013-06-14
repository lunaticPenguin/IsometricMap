package entities.factory;

public interface iFactory<T> {
	public T getEntity(String entityType);
	public boolean setEntityBack(String entityType, T entity);
}
