package com.hogo.portal.core;

import java.time.LocalDate;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Text;

public class Binder<T> {
	private boolean changed;
	private T bean;
	private Consumer<Object> event;

	public Binder() {
		super();
	}

	public Binder(Consumer<Object> consumer) {
		event = consumer;
	}

	public Binder(T bean, Consumer<Object> consumer) {
		this.bean = bean;
		this.event = consumer;
	}

	public T getBean() {
		return bean;
	}

	public void setBean(T bean) {
		this.bean = bean;
	}


	public Binder<T> bind(StyledText control, Function<T, String> getter, BiConsumer<T, String> setter) {
		if (bean != null && getter != null) {
			String value = getter.apply(bean);
			if (value != null)
				control.setText(value);
		}
		if (setter != null)
			control.addModifyListener(m -> {
				control.addModifyListener(e -> {
					onChange(e.getSource());
					setter.accept(bean, control.getText());
				});
			});
		return this;
	}

	public Binder<T> bind(Text control, Function<T, String> getter, BiConsumer<T, String> setter) {
		if (bean != null && getter != null) {
			String value = getter.apply(bean);
			if (value != null)
				control.setText(value);
		}
		if (setter != null)
			control.addModifyListener(m -> {
				control.addModifyListener(e -> {
					onChange(e.getSource());
					setter.accept(bean, control.getText());
				});
			});
		return this;
	}

	public Binder<T> bind(DateTime control, Function<T, LocalDate> getter, BiConsumer<T, LocalDate> setter) {
		if (bean != null && getter != null) {
			LocalDate value = getter.apply(bean);
			if( value == null )
				control.setDate(9999,0,1);
			else
				control.setDate(value.getYear(), value.getMonthValue()-1, value.getDayOfMonth());
		}
		
		if (setter != null) {
			control.addListener(SWT.CHANGED, e -> { 
				DateTime t = (DateTime)e.widget;
				if( t.getYear() < 9999 ) {
					onChange(e);
					setter.accept(bean, LocalDate.of(t.getYear(), t.getMonth()+1, t.getDay()));
				}
			});
		}
		return this;
	}
	
	public Binder<T> bind(Button control, Function<T, Boolean> getter, BiConsumer<T, Boolean> setter) {
		if (bean != null && getter != null) {
			boolean value = getter.apply(bean);
			control.setSelection(value);
		}
		if (setter != null) {
			control.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					onChange(e.getSource());
					setter.accept(bean, control.getSelection());
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					onChange(e.getSource());
					setter.accept(bean, control.getSelection());
				}
			});
		}
		return this;
	}

	public boolean isChanged() {
		return changed;
	}

	public void onChange(Object source) {
		changed = true;
		if (event != null)
			event.accept(source);
	}
	
	public void setChanged(boolean b) {
		changed = b;
	}
}
