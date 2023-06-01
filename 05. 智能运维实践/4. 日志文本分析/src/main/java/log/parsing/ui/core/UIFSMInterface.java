package log.parsing.ui.core;

import java.util.concurrent.BlockingQueue;

public abstract class UIFSMInterface {
	
	private String moduleName = null;
	
	protected BlockingQueue<UIEventInterface> eventQueue = null;
	
	public UIFSMInterface(String moduleName) {
		this.moduleName = moduleName;
	}
	
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	public void addEvent(Object data) throws Exception {
		eventQueue.add(new UICoreEvent(this.getModuleName(), null, data));
	}
	
	abstract public void runEvent(Object data) throws Exception;
}
