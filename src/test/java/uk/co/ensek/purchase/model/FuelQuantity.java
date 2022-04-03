package uk.co.ensek.purchase.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FuelQuantity
{
	@SerializedName("fuel")
	@Expose
	private String fuel;

	@SerializedName("id")
	@Expose
	private String id;

	@SerializedName("quantity")
	@Expose
	private String quantity;

	@SerializedName("time")
	@Expose
	private String time;

	public String getFuel()
	{
		return fuel;
	}

	public String getId()
	{
		return id;
	}

	public String getQuantity()
	{
		return quantity;
	}

	public String getTime()
	{
		return time;
	}

	public void setFuel(String fuel)
	{
		this.fuel = fuel;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setQuantity(String quantity)
	{
		this.quantity = quantity;
	}

	public void setTime(String time)
	{
		this.time = time;
	}
}
