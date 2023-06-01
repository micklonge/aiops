package log.parsing.ui.core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UICoreFSM extends UIFSMInterface implements Runnable {
	
	private static final Logger logger = LogManager.getLogger();
	
	private Map<String, UIFSMInterface> moduleFSMMap = new HashMap<String, UIFSMInterface>();

	public UICoreFSM(BlockingQueue<UIEventInterface> eventQueue) {
		super("TrainDataCoreFSM");
		this.eventQueue = eventQueue;
	}

	@Override
	public void run() {
		UICoreEvent coreEvent = null;
		
		while (true) {
			try {
				coreEvent = (UICoreEvent) eventQueue.poll(500, TimeUnit.MICROSECONDS);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
			
			if (coreEvent == null) {
				continue;
			}
			
			try {
				moduleFSMMap.get(coreEvent.getModuleName()).runEvent(coreEvent.getData());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	public void addCoreModuleInfo(String name, UIFSMInterface module) {
		moduleFSMMap.put(name, module);
	}

	@Override
	public void runEvent(Object data) throws Exception {
		logger.error("call the function is failed");
	}

}
