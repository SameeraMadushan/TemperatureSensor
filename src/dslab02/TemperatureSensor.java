interface TemperatureSensor extends java.rmi.Remote
{
	public double getTemperature() throws
		java.rmi.RemoteException; 			//method to request temperature on demand(client can request temperature)
	public void addTemperatureListener
		(TemperatureListener listener )		//add listners to the list
		throws java.rmi.RemoteException;
	public void removeTemperatureListener
		(TemperatureListener listener )
		throws java.rmi.RemoteException;	//Remove listner from the list
}

//Allow listners to be registered and unregistered