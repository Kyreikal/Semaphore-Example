/*You don't have permission to turn this in as your own work */
import java.util.concurrent.Semaphore;
import java.lang.Math;

class SemaphoreExample implements Runnable
{
	enum State {HUNGRY, EATING, THINKING};
	
	private State me;
	private int ident;
	public static int ate[] = {0,0,0,0,0};

	DiningPhilosophers(int i)
	{
		me = State.THINKING;
		ident = i;
	}

	public static Semaphore chopstick[] = new Semaphore[]
	{
		new Semaphore(1),
		new Semaphore(1),
		new Semaphore(1),
		new Semaphore(1),
		new Semaphore(1)
	};
	

	public void eat(int a, int b)
	{			
		try
		{
			chopstick[a].acquire();
			chopstick[b].acquire();
			this.me = State.EATING;
			System.out.printf("Phil %d is now eating\n", this.ident);
			//Thread.sleep(10);
		}
		catch(InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
		chopstick[a].release();
		chopstick[b].release();
	}

	public void run()
	{
		int left, right;
		if(this.ident == 5) left = 0;
		else left = this.ident;

		right = this.ident - 1;
		int k = 0;
		while(k < 10000)
		{
			System.out.printf("Phil %d, is thinking\n", this.ident);
			//if (k % 100 == 0) System.out.printf(".");
			try
			{
				int i = (int)(Math.random() * 100 + 1);
				Thread.sleep(i);
			}
			catch(InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
			this.me = State.HUNGRY;
			System.out.printf("Phil %d, is HUNGRY\n", this.ident);
			while(this.me == State.HUNGRY)
			{
				//if(chopstick[left] == 0 && chopstick[right] == 0);
				//{
					eat(left, right);
					this.me = State.THINKING;
				//}
				ate[this.ident -1]++;	
			}
			k++;
		}
	}
  
  /** @author Daniel Doucett */
	public static void main(String[] args)
	{
		DiningPhilosophers d[]= new DiningPhilosophers[]
		{
			new DiningPhilosophers(1),
			new DiningPhilosophers(2),
			new DiningPhilosophers(3),
			new DiningPhilosophers(4),
			new DiningPhilosophers(5)
		};

		Thread t[] = new Thread[]
		{
			new Thread(d[0]),
			new Thread(d[1]),
			new Thread(d[2]),
			new Thread(d[3]),
			new Thread(d[4]),
		};
    //Writen By Daniel Doucett
		for (Thread x: t)
		{
			x.start();
		}
		for (Thread x: t)
		{
			try
			{
				x.join();
			}
			catch(InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
		}
		
		for (int i = 0; i < 5; i++)
		{
			System.out.printf("Phil %d ate %d times.\n", i, ate[i]);
		}
	}
}
