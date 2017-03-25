interface TemperatureListener extends java.rmi.Remote
{
	public void temperatureChanged(double temperature) throws 	java.rmi.RemoteException;  
}

//The listner define a remote object with a single method
//Method i invoked by event souce whenever event occurs, so act as notification that event occurred
//The method signifies a change in temperature and allows the new temperature to be passed as a parameter