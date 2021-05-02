package ru.ecom.report.replace;

import org.apache.log4j.Logger;
import ru.nuzmsh.util.PropertyUtil;

/**
 *
 */
public class ReplaceHelper {

    private static final Logger LOG = Logger.getLogger(ReplaceHelper.class);

    /**
     * Режим RTF
     */
    public boolean getRtfMode() {
        return theRtfMode;
    }

    public void setRtfMode(boolean aRtfMode) {
        theRtfMode = aRtfMode;
    }

    /**
     * Режим RTF
     */
    private boolean theRtfMode = false;

    public Object replaceWithValues(String aLine, IValueGetter aValueGetter) throws SetValueException {
        if (aLine.startsWith("${") && aLine.indexOf("${", 3) == -1 && aLine.endsWith("}")) {
            try {
                return aValueGetter.getValue(aLine.substring(2, aLine.length() - 1));
            } catch (Exception e) {
                throw new SetValueException(e.getMessage(), e.getCause());
            }
        } else {
            if (aLine.contains("$\\{") || aLine.contains("${")) {
                StringBuilder sb = new StringBuilder(aLine);
                int result = 0;

                while ((result = replace(sb, result, aValueGetter, theRtfMode)) >= 0) {

                }
                return sb.toString();
            } else {
                return aLine;
            }
        }
    }


    private static int replace(StringBuilder aSb, int aFromIndex, IValueGetter aValueGetter, boolean aRtfMode) throws SetValueException {
        int from = aSb.indexOf(aRtfMode ? "$\\{" : "${", aFromIndex);
        if (from > -1) {
            int to = aSb.indexOf(aRtfMode ? "\\}" : "}", from + 1);
            if (to > -1 && to > from) {
                String key = aSb.substring(aRtfMode ? from + 1 : from, to);
                key = stripKey(key);
                Object value = aValueGetter.getValue(key);
                String strValue;
                if (value == null) {
                    strValue = "";
                } else {
                    try {
                        strValue = (String) PropertyUtil.convertValue(value.getClass(), String.class, value);
                    } catch (Exception e) {
                        LOG.warn("Ошибка преобразования", e);
                        strValue = value.toString();
                    }
                }
                aSb.delete(from, aRtfMode ? to + 2 : to + 1);
                aSb.insert(from, strValue);
                return from + strValue.length();
            }
        }
        return -1;
    }

    private static String stripKey(String aStr) {
        if (aStr != null && aStr.length() > 3) {
            return aStr.substring(2);
        } else {
            return aStr;
        }
    }

}
