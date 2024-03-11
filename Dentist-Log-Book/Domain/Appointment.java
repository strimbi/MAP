package Domain;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Appointment extends Entity implements Serializable {
    private Pacient pacient;
    private String date;
    private int hour;
    private String motive;

    public Appointment(int id_app, Pacient pacient, String date, int hour, String motive)
    {
        super(id_app);
        this.pacient = pacient;
        this.date = date;
        this.hour = hour;
        this.motive = motive;
    }

    @Override
    public String toString()
    {
        return "Appointment=(" + " id appointment=" + getId() + " || " + this.pacient.toString()
                + " || " + "date=" + this.date + " || " + "hour=" + this.hour + " || "
                + "motive=" + this.motive + " )";
    }

    public Pacient getPacient()
    {
        return this.pacient;
    }

    public String getDate()
    {
        return this.date;
    }

    public int getHour()
    {
        return this.hour;
    }

    public String getMotive()
    {
        return this.motive;
    }

    @Override
    public boolean equals(Object O)
    {
        if (this == O) return true;
        if (O == null || getClass() != O.getClass()) return false;
        Appointment appointment = (Appointment) O;
        return  this.getId() == appointment.getId() &&
                this.pacient.equals(appointment.pacient)  &&
                Objects.equals(this.date, appointment.date) &&
                this.hour == appointment.hour &&
                Objects.equals(this.motive, appointment.motive);
    }
}