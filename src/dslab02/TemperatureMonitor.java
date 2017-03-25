import java.rmi.*;
import java.rmi.server.*;
import java.net.*;

//Implement the TemperatureListner interface and register itself with remote temperature sensor by invoking Temperature.addTemperatureListner(Temperature Listner)BY Registering as a listner the monitor will be notified of changes as they occure using remote cal back
public class TemperatureMonitor extends UnicastRemoteObject implements
		TemperatureListener, Runnable {

	private int count = 0;

	public TemperatureMonitor() throws RemoteException {
	}

	public static void main(String[] args) {

		try {
			String registration = "//localhost/TemperatureSensor";
//Lookup the registry in the service and obtain the remote service
			Remote remoteService = Naming.lookup(registration);
			TemperatureSensor sensor = (TemperatureSensor) remoteService;
			double reading = sensor.getTemperature();
			System.out.println("Original temp : " + reading);
			TemperatureMonitor monitor = new TemperatureMonitor();
			sensor.addTemperatureListener(monitor);		 //Registering your listner as the client
			monitor.run();									//execute the run method
		} catch (MalformedURLException mue) {
		} catch (RemoteException re) {
		} catch (NotBoundException nbe) {
		}
	}

	public void temperatureChanged(double temperature)
			throws java.rmi.RemoteException {
		System.out.println("\nTemperature change event : " + temperature);
		count = 0;
	}

	public void run() {
		for (;;) {
			count++;

		// note that this might only work on windows console
			System.out.print("\r" + count);
			try {
				Thread.sleep(100);
			} catch (InterruptedException ie) {
			}

		}

	}
}