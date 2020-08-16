import {html} from
   '@polymer/polymer/lib/utils/html-tag.js';
import {TextFieldElement} from
   '@vaadin/vaadin-text-field/src/vaadin-text-field.js';

import flatpickr from 'flatpickr';

require("flatpickr/dist/flatpickr.min.css");
require("flatpickr/dist/l10n/zh.js");
require("flatpickr/dist/l10n/ru.js");
require("flatpickr/dist/l10n/fr.js");
require("flatpickr/dist/l10n/de.js");
require("flatpickr/dist/l10n/it.js");
require("flatpickr/dist/l10n/ja.js");

let memoizedTemplate;

class VDatePicker extends TextFieldElement {
	static get template() {
        if (!memoizedTemplate) {
            const superTemplate = super.template
                    .cloneNode(true);
            memoizedTemplate = html`
               ${superTemplate}`;
        }
        return memoizedTemplate;
    }
	
	static get is() {
        return 'v-datepicker';
    }
	
	ready() {
		 super.ready();
		 setTimeout(this.initDate.bind(this), 15);
		 this.els = this.shadowRoot.querySelectorAll("div");
		 this._inputElement = this.shadowRoot.querySelector("input");
		 if(this.inline)
		 {
			 this.els[0].style.display = 'none';
			 this.els[1].style.display = 'none';
		 }
		 this._inputElement.addEventListener('change', () => {
			 this.validate();
		 }, this);
	}
	
	initDate() {
		var el = this.shadowRoot;
		var suffixEl = el.querySelectorAll("slot")[2];
		var inputFld = el.querySelector("input");
		inputFld.setAttribute("data-input","");
		inputFld.removeAttribute("readonly");
		var max = undefined;
		var min = undefined;
		if(this.format) {
			if(this.maxDate) {
				max = flatpickr.parseDate(this.maxDate,this.format)
			}
			if(this.minDate) {
				min = flatpickr.parseDate(this.minDate,this.format)
			}
		}
		this.config = {
			clickOpens:false,
			enableTime: this.includeTime || !this.includeDate,
		    allowInput: true,
		    enableSeconds: this.includeTime,
		    dateFormat:this.format,
		    locale:this.locale || 'en',
		    maxDate: this.maxDate,
		    minDate: this.minDate,
		    mode: this.mode || 'single',
		    noCalendar: !this.includeDate,
		    inline: this.inline,
		    minDate: min,
		    maxDate: max,
		    static:false,
		    time_24hr: this.time_24hr || false
		}
		this.pickr = inputFld.flatpickr(this.config);
		var dateEl = this;
		suffixEl.style.cursor = 'pointer';
		suffixEl.onclick = function() {
			if(!dateEl.readonly) {
				dateEl.pickr.open();
				if(dateEl.pickr.calendarContainer)
					dateEl.pickr.calendarContainer.focus();
			}
		}
		inputFld.onfocus = function() {
			if(dateEl.pickr)
				dateEl.pickr.close();
		}
	}
	
	disconnectedCallback() {
        super.disconnectedCallback();
        if(this.pickr)
        	this.pickr.destroy();
        this.pickr = null;
    }
	
	updateCalendar(date) {
		if(this.pickr) {
			this.pickr.setDate(date,false,this.format);
		}
	}
	
	setMax(date) {
		if(this.pickr) {
			var value = date?flatpickr.parseDate(date, this.format):null;
			this.pickr.maxDate = value;
			this.pickr.redraw();
		}
	}
	
	setMin(date) {
		if(this.pickr) {
			var value = date?flatpickr.parseDate(date, this.format):null;
			this.pickr.minDate = value;
			this.pickr.redraw();
		}
	}
	
	validate() {
		this.$server.parseDate(this._inputElement.value)
	}
	
	_input() {
		return this.$.input;
	}

	set _inputValue(value) {
		this._inputElement.value = value;
	}

	get _inputValue() {
		return this._inputElement.value;
	}
}

window.customElements.define(VDatePicker.is, VDatePicker);