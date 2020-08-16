package com.sm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.CompositionNotifier;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.component.InputNotifier;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.HasPrefixAndSuffix;
import com.vaadin.flow.data.value.HasValueChangeMode;
import com.vaadin.flow.data.value.ValueChangeMode;

/**
 * This is a date time picker component based on Vaadin flow framework & flatpickr js widget.
 * The component is a single popup view which accepts both date and time. It supports locale and
 * date formats. It also serve as a time picker. 
 * 
 * Check flatpickr documentation for more details.
 * @author smore
 *
 */
@Tag("v-datepicker")
@CssImport("./vdatepicker/vdatepicker.css")
@JsModule("./vdatepicker/vdatepicker.js")
@NpmPackage.Container(
{ @com.vaadin.flow.component.dependency.NpmPackage(value = "flatpickr", version = "4.6.3") })
public class VDatePicker extends AbstractSinglePropertyField<VDatePicker, Date>
		implements HasStyle, Focusable<VDatePicker>, HasTheme, HasSize, HasValidation, HasValueChangeMode,
		HasPrefixAndSuffix, InputNotifier, KeyNotifier, CompositionNotifier
{
	private static final long serialVersionUID = 3464039785953141720L;

	public static final String DATETIME_FORMAT = "dd-MMM-yyyy hh:mm:ss a";
	public static final String DATE_FORMAT = "dd-MMM-yyyy";

	private ValueChangeMode currentMode;
	private String format;
	private Date max;
	private Date min;

	public enum SelectionMode
	{
		SINGLE, RANGE
	}

	public VDatePicker()
	{
		super("value", null, String.class, s -> VDatePicker.parse(s, DATETIME_FORMAT),
				d -> VDatePicker.format(d, DATETIME_FORMAT));
		setFormat(DATETIME_FORMAT);
		setIncludeDate(true);
		setIncludeTime(false);
		Icon cal = VaadinIcon.CALENDAR.create();
		cal.getElement().setAttribute("slot", "suffix");
		getElement().appendChild(cal.getElement());
	}

	/**
	 * This constructor accepts the date format to be used for displaying date
	 * 
	 * @param dateformat
	 *            the format for parsing & formatting date, should be
	 *            SimpleDateFormat pattern.
	 */
	public VDatePicker(String format)
	{
		super("value", null, String.class, s -> VDatePicker.parse(s, format), d -> VDatePicker.format(d, format));
		setFormat(format);
		setIncludeDate(true);
		setIncludeTime(false);
		Icon cal = VaadinIcon.CALENDAR.create();
		cal.getElement().setAttribute("slot", "suffix");
		getElement().appendChild(cal.getElement());
	}

	/**
	 * This constructor accepts the label & date format to be used for
	 * displaying date
	 * 
	 * @param dateformat
	 *            the format for parsing & formatting date, should be
	 *            SimpleDateFormat pattern.
	 */
	public VDatePicker(String label, String format)
	{
		this(format);
		setLabel(label);
	}

	private static String format(Date value, String format)
	{
		String date = null;
		if (value != null)
		{
			try
			{
				SimpleDateFormat fmt = new SimpleDateFormat(format);
				fmt.setLenient(false);
				date = fmt.format(value);
			}
			catch (Exception e)
			{

			}
		}
		return date;
	}

	private static Date parse(String value, String format)
	{
		Date date = null;
		if (value != null && !value.trim().isEmpty())
		{
			try
			{
				if (value.length() == format.length()
						|| (format.endsWith(" a") && value.endsWith("M") && value.length() == (format.length() + 1)))
				{
					SimpleDateFormat smt = new SimpleDateFormat(format);
					smt.setLenient(false);
					date = smt.parse(value);
				}
				else
					return date;
			}
			catch (Exception e)
			{

			}

		}
		return date;
	}

	/**
	 * 7 locales are added in dependency along with this addon ENGLISH, GERMAN,
	 * FRENCH, JAPANESE, CHINESE, ITALIAN, RUSSIAN
	 * 
	 * @param locale
	 */
	public void setLocale(Locale locale)
	{
		getElement().setProperty("locale", locale == null ? "en" : locale.getLanguage());
	}

	public void setPlaceholder(String placeholder)
	{
		getElement().setProperty("placeholder", placeholder == null ? "" : placeholder);
	}

	public String getPlaceholderString()
	{
		return getElement().getProperty("placeholder");
	}

	public boolean isReadonlyBoolean()
	{
		return getElement().getProperty("readonly", false);
	}

	public void setReadonly(boolean readonly)
	{
		getElement().setProperty("readonly", readonly);
	}

	public void addTheme(String theme)
	{
		getElement().getThemeList().add(theme);
	}

	private void setFormat(String format)
	{
		this.format = format;
		String clFormat = convertToJsFormat(format);
		getElement().setProperty("format", clFormat == null ? "d-M-Y" : clFormat);
	}

	/**
	 * This function converts the date format to flatpickr date pattern at
	 * client side
	 * 
	 * @return
	 */
	private String convertToJsFormat(String format)
	{
		if (format == null || format.trim().isEmpty())
			return "d-M-Y";

		// date conversion
		format = format.replace("dd", "d");
		format = format.replace("DDD", "D");
		format = format.replace("DD", "d");

		// month conversion
		format = format.replace("MMM", "M");
		format = format.replace("MM", "m");

		format = format.replace("yyyy", "Y");
		format = format.replace("YYYY", "y");
		format = format.replace("yy", "y");

		format = format.replace("HH", "H");
		format = format.replace("hh", "G");

		format = format.replace("mm", "i");
		format = format.replace("SS", "S");
		format = format.replace("ss", "S");

		format = format.replace(" a", " K");
		format = format.replace(" A", " K");
		return format;
	}

	public boolean isInvalid()
	{
		if (isRequired() && getValue() == null)
			return true;
		return false;
	}

	@Override
	public ValueChangeMode getValueChangeMode()
	{
		return currentMode;
	}

	@Override
	public void setValueChangeMode(ValueChangeMode valueChangeMode)
	{
		currentMode = valueChangeMode;
		setSynchronizedEvent(ValueChangeMode.eventForMode(valueChangeMode, "value-changed"));
		applyChangeTimeout();
	}

	private void applyChangeTimeout()
	{
		ValueChangeMode.applyChangeTimeout(getValueChangeMode(), getValueChangeTimeout(),
				getSynchronizationRegistration());
	}

	@Override
	public void setErrorMessage(String errorMessage)
	{
		getElement().setProperty("errorMessage", errorMessage == null ? "" : errorMessage);
	}

	@Override
	public String getErrorMessage()
	{
		return getElement().getProperty("errorMessage");
	}

	@Override
	public void setInvalid(boolean invalid)
	{
		getElement().setProperty("invalid", invalid);
	}

	public void setLabel(String label)
	{
		getElement().setProperty("label", label == null ? "" : label);
	}

	public String getLabelString()
	{
		return getElement().getProperty("label");
	}

	public void addToPrefix(Component... components)
	{
		for (Component component : components)
		{
			component.getElement().setAttribute("slot", "prefix");
			getElement().appendChild(component.getElement());
		}
	}

	/**
	 * The calendar icon is part of suffix component, hence this method is
	 * overriden to avoid conflict with the suffix component.
	 */
	public void setSuffixComponent(Component component)
	{
		// DO NOT ADD ANYTHING IN SUFFIX
	}

	public String getTitleString()
	{
		return getElement().getProperty("title");
	}

	public void setTitle(String title)
	{
		getElement().setProperty("title", title == null ? "" : title);
	}

	public void setRequired(boolean required)
	{
		getElement().setProperty("required", required);
	}

	public boolean isRequired()
	{
		return getElement().getProperty("required", false);
	}

	public void validate()
	{
		setInvalid(isInvalid());
	}

	public void setIncludeTime(boolean includeTime)
	{
		getElement().setProperty("includeTime", includeTime);
	}

	public boolean includeTime()
	{
		return getElement().getProperty("includeTime", false);
	}

	public void setIncludeDate(boolean includeDate)
	{
		getElement().setProperty("includeDate", includeDate);
	}

	public boolean includeDate(boolean includeDate)
	{
		return getElement().getProperty("includeDate", true);
	}

	public void setMode(SelectionMode mode)
	{
		getElement().setProperty("mode", mode == SelectionMode.RANGE ? "range" : "single");
	}

	public void setInline(boolean inline)
	{
		getStyle().set("height", "unset");
		getElement().setProperty("inline", inline);
	}

	@ClientCallable
	public void parseDate(String value)
	{
		try
		{
			this.setErrorMessage(null);
			setInvalid(false);
			if (value == null || value.trim().isEmpty())
			{
				setValue(null);
				validate();
				return;
			}
			Date date = parse(value, format);
			if (date != null)
			{
				setValue(date);
			}
			else
			{
				setInvalid(true);
				setErrorMessage("Invalid date input");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void setValue(Date value)
	{
		setInvalid(false);
		setErrorMessage(null);
		if (max != null && value != null && max.before(value))
		{
			super.setValue(value);
			setInvalid(true);
			setErrorMessage("value is greater than max date");
		}
		else if (min != null && value != null && value.before(min))
		{
			super.setValue(value);
			setInvalid(true);
			setErrorMessage("value is less than min date");
		}
		else
		{
			super.setValue(value);
			getElement().executeJs("if(this.updateCalendar) {this.updateCalendar($0)}",
					value == null ? "" : format(value, format));
		}
	}

	public void setMaxValue(Date max)
	{
		this.max = max;
		getElement().setProperty("maxDate", max != null ? format(max, format) : null);
		getElement().executeJs("this.setMax($0)", format(max, format));
	}

	public void setMinValue(Date min)
	{
		this.min = min;
		getElement().setProperty("minDate", min != null ? format(min, format) : null);
		getElement().executeJs("this.setMin($0)", format(min, format));
	}

	public boolean isValid()
	{
		return !isInvalid();
	}
}