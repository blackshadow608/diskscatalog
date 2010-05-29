package disks.catalog.model;

import disks.catalog.model.action.DBAction;
import disks.catalog.model.action.IDBActionListener;

/**
 * Адаптер {@link DBActionAdapter} представляет собой пустую
 * реализацию интерфейса {@link IDBActionListener}. Если необходимо создать
 * слушателя и есть возможность пронаследоваться от {@link DBActionAdapter}, то
 * лучше сделать так. После этого останется только переопределить нужные методы.
 * Если такой возможности нет, то реализовать интерфейс {@link IDBActionListener}.
 * Основное назначение класса - не плодить ненужный код в виде списка пустых методов.
 * 
 * @author Alexander Levin
 */
public class DBActionAdapter implements IDBActionListener {

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
