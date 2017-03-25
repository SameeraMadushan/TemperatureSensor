import java.util.*;
import java.rmi.*;
import java.rmi.server.*;

public class TemperatureSensorServer extends UnicastRemoteObject implements		
		TemperatureSensor, Runnable {

	private volatile double temp;
	private ArrayList<TemperatureListener> list = new ArrayList<TemperatureListener>();		//List of registered listners are registered in the array list

	public TemperatureSensorServer() throws java.rmi.RemoteException {
		temp = 98.0;
	}

	public double getTemperature() throws java.rmi.RemoteException {
		return temp;
	}

	public void addTemperatureListener(TemperatureListener listener)
			throws java.rmi.RemoteException {
		System.out.println("adding listener -" + listener);
		list.add(listener);
	}

	public void removeTemperatureListener(TemperatureListener listener)
			throws java.rmi.RemoteException {
		System.out.println("removing listener -" + listener);
		list.remove(listener);
	}

	public void run() {
		Random r = new Random();
		for (;;) {
			try {
				// Sleep for a random amount of time
				int duration = r.nextInt() % 10000 + 200;
				// Check to see if negative, if so, reverse
				if (duration < 0) {
					duration = duration * -1;		//how long you are going to pass the thread
					Thread.sleep(duration);
				}
			} catch (InterruptedException ie) {
			}

			// Get a number, to see if temp goes up or down
			int num = r.nextInt();
			if (num < 0) {
				temp += 0.5;
			} else {
				temp -= 0.5;
			}

			// Notify registered listeners
			notifyListeners();
		}
	}

	private void notifyListeners() {
		// TO DO: Notify every listener in the registered list if there is a change in the temperature
		Iterator<TemperatureListener> ite = list.iterator();
		while(ite.hasNext()){
			TemperatureListener listner = ite.next();
			//notify, if possible alistner
			try{
				listner.temperatureChanged(temp);
			}catch(RemoteException re){
				System.out.println("Removing Listner - "+ listner);
				//Remove the listner list.remove(listner);
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("Loading temperature service");

		try {
			TemperatureSensorServer sensor = new TemperatureSensorServer();
			String registry = "localhost";

			String registration = "rmi://" + registry + "/TemperatureSensor";   //register the format //registry hostname: port/service

			Naming.rebind(registration, sensor);  //Register the service(Naming.renind)

			Thread thread = new Thread(sensor);
			thread.start();
		} catch (RemoteException re) {
			System.err.println("Remote Error - " + re);
		} catch (Exception e) {
			System.err.println("Error - " + e);
		}

	}

}