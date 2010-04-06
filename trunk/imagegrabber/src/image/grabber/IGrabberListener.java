package image.grabber;

public interface IGrabberListener {
	
	public void started(Grabber grabber);
	
	public void stateChanged(Grabber grabber);
	
	public void stopped(Grabber grabber);
	
	public void error(Grabber grabber, String message);

}
