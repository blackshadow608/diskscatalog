package disks.catalog.model;

/**
 * Абстрактный адаптер <b>IDBActionAdapter</b> представляет собой пустую
 * реализацию интерфейса <b>IDBActionListener</b>. Если необходимо создать
 * слушателя и есть возможность пронаследоваться от <b>IDBActionAdapter</b>, то
 * лучше сделать так. После этого останется только переопределить нужные методы.
 * Если такой возможности нет, то реализовать интерфейс <b>IDBActionListener</b>.
 * 
 * @author Alexander Levin
 */
public abstract class IDBActionAdapter implements IDBActionListener {

	public void actionThrowException(DBAction dbAction, Exception exception) {

	}

	public void actionWasAborted(DBAction dbAction) {

	}

	public void afterActionExecuting(DBAction dbAction) {

	}

	public void beforeActionExecuting(DBAction dbAction) {

	}

	public void redoBecameDisabled() {

	}

	public void redoBecameEnabled() {

	}

	public void someActionWasAdded() {

	}

	public void undoBecameDisabled() {

	}

	public void undoBecameEnabled() {

	}

}
