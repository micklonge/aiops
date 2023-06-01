package log.parsing.ui.core;

public class UICoreEvent extends UIEventInterface {
	
	private UICoreEnum eventEnum = null;
	private Object data = null;
	
	public UICoreEvent(String moduleName, UICoreEnum eventEnum, Object data) {
		this.eventEnum = eventEnum;
		this.data = data;
		setModuleName(moduleName);
	}
	
	public UICoreEvent(String moduleName, UICoreEnum eventEnum) {
		this.eventEnum = eventEnum;
		setModuleName(moduleName);
	}
	
	public UICoreEnum getEventEnum() {
		return eventEnum;
	}
	
	public void setEventEnum(UICoreEnum eventEnum) {
		this.eventEnum = eventEnum;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}

}
