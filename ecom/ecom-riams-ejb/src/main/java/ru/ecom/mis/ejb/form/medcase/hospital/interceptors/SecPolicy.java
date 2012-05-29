package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJBException;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;

import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.nuzmsh.util.format.DateConverter;
import ru.nuzmsh.util.format.DateFormat;

/**
 * Утилита для проверки даты в районе меньше 24 часов
 */
public class SecPolicy {

    private final static Logger LOG = Logger.getLogger(SecPolicy.class);
    private final static boolean CAN_TRACE = LOG.isDebugEnabled() ;

    /**
     * Проверка:
     *   1. введенная дата <=  текущая дата
     *   2. введенная дата >   текущая дата - 24 часа
     *
     * @param aContext
     * @param aHourPolicy
     * @param aDateString
     * @param aTimeString
     * @throws IllegalStateException 
     */
    public static void checkPolicyCreateHour(SessionContext aContext, String aDateString, String aTimeString) {
        if (aContext.isCallerInRole(StatisticStubStac.CreateHour)) {
        	try {
                Date enteredDate = DateConverter.createDateTime(aDateString, aTimeString);
                checkPolicyCreateHour(enteredDate);
        	} catch (Exception e){
                throw new IllegalStateException(
                		new StringBuffer().append("Неправильно введенна дата поступления или время. ")
                		.append(e).toString()) ;
        	}
        }
    }
    /**
     * Проверка:
     *   1. введенная дата <=  текущая дата
     *   2. введенная дата >   текущая дата - 24 часа
     *
     * @param aEnteredDate         введенная дата
     * @throws IllegalStateException
     */
    private static void checkPolicyCreateHour(Date aEnteredDate) {
        if(isDateLessThen24Hour(aEnteredDate)) {
            throw new IllegalStateException("Дата поступления меньше на 24 часа, чем текущая дата") ;
        }
    }

    /**
     * Проверка на текущую дату и дату поступления
     *
     * @param aContext
     * @param aPolicy
     * @param aSls
     * @throws IllegalStateException
     */
    public static void checkPolicyEditHour(SessionContext aContext , HospitalMedCase aSls) {
        if(CAN_TRACE) {
            LOG.debug("aContext = " + aContext);
            LOG.debug("aPolicy = " + StatisticStubStac.EditHour);
        }

        if (aContext.isCallerInRole(StatisticStubStac.EditHour)) {
            if (CAN_TRACE) LOG.debug(" У пользователя есть роль = " + StatisticStubStac.EditHour);
            Date admissionDate = DateConverter.createDateTime(aSls.getDateStart(), DateFormat.formatToTime(aSls.getEntranceTime()));
            if (CAN_TRACE) LOG.debug(" Дата поступления = " + admissionDate);
            if (admissionDate != null) {
                if(isDateLessThen24Hour(admissionDate)) {
                    throw new IllegalStateException("Дата поступления меньше 24 часа, чем текущая. Изменять информацию нельзя") ;
                }
            } else {
                throw new EJBException("Дата поступления не введена") ;
            }
        }
    }

    public static boolean isDateLessThen24Hour(Date aDate) {
        Date currentDate = new Date();
        if(currentDate.getTime() < aDate.getTime()) {
            throw new IllegalStateException("Введенная дата не может быть больше текущей");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.HOUR_OF_DAY, -24);
        if(CAN_TRACE) {
            logDate("startDate", aDate) ;
            logDate("currentDate  ",  currentDate);
            logDate("cal.getTime()", cal.getTime());
            LOG.debug("cal.getTime().getTime() < aDate.getTime() = " + (cal.getTime().getTime() < aDate.getTime()));
        }
        return aDate.getTime() < cal.getTime().getTime()  ;
    }


    private static void logDate(String aName, Date aDate) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        LOG.debug(aName+ " "+f.format(aDate));
    }
}
