jQuery(function(a){var b={init:function(){a("body").on("change","#wc_bookings_field_duration, #wc_bookings_field_resource",this.date_picker_init),a("body").on("click",".wc-bookings-date-picker legend small.wc-bookings-date-picker-choose-date",this.toggle_calendar),a("body").on("input",".booking_date_year, .booking_date_month, .booking_date_day",this.input_date_trigger),a("body").on("change",".booking_to_date_year, .booking_to_date_month, .booking_to_date_day",this.input_date_trigger),a(".wc-bookings-date-picker legend small.wc-bookings-date-picker-choose-date").show(),a(".wc-bookings-date-picker").each(function(){var c=a(this).closest("form"),d=c.find(".picker"),e=a(this).closest("fieldset");b.date_picker_init(d),"always_visible"==d.data("display")?(a(".wc-bookings-date-picker-date-fields",e).hide(),a(".wc-bookings-date-picker-choose-date",e).hide()):d.hide(),d.data("is_range_picker_enabled")&&(c.find("p.wc_bookings_field_duration").hide(),c.find(".wc_bookings_field_start_date legend span.label").text("always_visible"!==d.data("display")?booking_form_params.i18n_dates:booking_form_params.i18n_start_date))})},calc_duration:function(a){var b=a.closest("form"),c=a.closest("fieldset"),d=a.data("duration-unit");setTimeout(function(){var a=1,e=parseInt(c.find("input.booking_to_date_year").val()),f=parseInt(c.find("input.booking_to_date_month").val()),g=parseInt(c.find("input.booking_to_date_day").val()),h=parseInt(c.find("input.booking_date_year").val()),i=parseInt(c.find("input.booking_date_month").val()),j=parseInt(c.find("input.booking_date_day").val());if(e&&f>=0&&g&&h&&i>=0&&j){var k=new Date(h,i-1,j),l=new Date(e,f-1,g);k.setUTCHours(0),l.setUTCHours(0),a=Math.floor((l.getTime()-k.getTime())/864e5),"day"===d&&(a+=1)}b.find("#wc_bookings_field_duration").val(a).change()})},toggle_calendar:function(){$picker=a(this).closest("fieldset").find(".picker:eq(0)"),b.date_picker_init($picker),$picker.slideToggle()},input_date_trigger:function(){var b=a(this).closest("fieldset"),c=b.find(".picker:eq(0)"),d=(a(this).closest("form"),parseInt(b.find("input.booking_date_year").val(),10)),e=parseInt(b.find("input.booking_date_month").val(),10),f=parseInt(b.find("input.booking_date_day").val(),10);if(d&&e&&f){var g=new Date(d,e-1,f);if(c.datepicker("setDate",g),c.data("is_range_picker_enabled")){var h=parseInt(b.find("input.booking_to_date_year").val(),10),i=parseInt(b.find("input.booking_to_date_month").val(),10),j=parseInt(b.find("input.booking_to_date_day").val(),10),k=new Date(h,i-1,j);!k||g>k?(b.find("input.booking_to_date_year").val("").addClass("error"),b.find("input.booking_to_date_month").val("").addClass("error"),b.find("input.booking_to_date_day").val("").addClass("error")):b.find("input").removeClass("error")}b.triggerHandler("date-selected",g)}},select_date_trigger:function(c){var d=a(this).closest("fieldset"),e=d.find(".picker:eq(0)"),f=a(this).closest("form"),g=c.split("-"),h=e.data("start_or_end_date");e.data("is_range_picker_enabled")&&h||(h="start"),"end"===h?(e.data("min_date",e.data("o_min_date")),d.find("input.booking_to_date_year").val(g[0]),d.find("input.booking_to_date_month").val(g[1]),d.find("input.booking_to_date_day").val(g[2]).change(),e.data("is_range_picker_enabled")&&b.calc_duration(e),e.data("start_or_end_date","start"),e.data("is_range_picker_enabled")&&f.find(".wc_bookings_field_start_date legend span.label").text("always_visible"!==e.data("display")?booking_form_params.i18n_dates:booking_form_params.i18n_start_date),"always_visible"!==e.data("display")&&a(this).hide()):(e.data("is_range_picker_enabled")&&(e.data("o_min_date",e.data("min_date")),e.data("min_date",c)),d.find("input.booking_to_date_year").val(""),d.find("input.booking_to_date_month").val(""),d.find("input.booking_to_date_day").val(""),d.find("input.booking_date_year").val(g[0]),d.find("input.booking_date_month").val(g[1]),d.find("input.booking_date_day").val(g[2]).change(),e.data("is_range_picker_enabled")&&b.calc_duration(e),e.data("start_or_end_date","end"),e.data("is_range_picker_enabled")&&f.find(".wc_bookings_field_start_date legend span.label").text(booking_form_params.i18n_end_date),"always_visible"===e.data("display")||e.data("is_range_picker_enabled")||a(this).hide()),d.triggerHandler("date-selected",c,h)},date_picker_init:function(c){if(a(c).is(".picker"))var d=a(c);else var d=a(this).closest("form").find(".picker:eq(0)");d.empty().removeClass("hasDatepicker").datepicker({dateFormat:a.datepicker.ISO_8601,showWeek:!1,showOn:!1,beforeShowDay:b.is_bookable,onSelect:b.select_date_trigger,minDate:d.data("min_date"),maxDate:d.data("max_date"),defaultDate:d.data("default_date"),numberOfMonths:1,showButtonPanel:!1,showOtherMonths:!0,selectOtherMonths:!0,closeText:wc_bookings_booking_form.closeText,currentText:wc_bookings_booking_form.currentText,prevText:wc_bookings_booking_form.prevText,nextText:wc_bookings_booking_form.nextText,monthNames:wc_bookings_booking_form.monthNames,monthNamesShort:wc_bookings_booking_form.monthNamesShort,dayNames:wc_bookings_booking_form.dayNames,dayNamesShort:wc_bookings_booking_form.dayNamesShort,dayNamesMin:wc_bookings_booking_form.dayNamesMin,firstDay:wc_bookings_booking_form.firstDay,gotoCurrent:!0}),a(".ui-datepicker-current-day").removeClass("ui-datepicker-current-day");var e=d.closest("form"),f=parseInt(e.find("input.booking_date_year").val(),10),g=parseInt(e.find("input.booking_date_month").val(),10),h=parseInt(e.find("input.booking_date_day").val(),10);if(f&&g&&h){var i=new Date(f,g-1,h);d.datepicker("setDate",i)}},get_input_date:function(a,b){var c=a.find("input.booking_"+b+"date_year"),d=a.find("input.booking_"+b+"date_month"),e=a.find("input.booking_"+b+"date_day");return 0!==c.val().length&&0!==d.val().length&&0!==e.val().length?c.val()+"-"+d.val()+"-"+e.val():""},is_bookable:function(c){var d=a(this).closest("form"),e=d.find(".picker:eq(0)"),f=a(this).data("availability"),g=a(this).data("default-availability"),h=a(this).data("fully-booked-days"),i=a(this).data("buffer-days"),j=a(this).data("partially-booked-days"),k=wc_bookings_booking_form.check_availability_against,l="",m="";if(d.find("select#wc_bookings_field_resource").val()>0)var n=d.find("select#wc_bookings_field_resource").val();else var n=0;var o=wc_bookings_booking_form.booking_duration,p=new Date(c),q=p.getFullYear(),r=p.getMonth()+1,s=p.getDate();if(h[q+"-"+r+"-"+s]&&(h[q+"-"+r+"-"+s][0]||h[q+"-"+r+"-"+s][n]))return[!1,"fully_booked",booking_form_params.i18n_date_fully_booked];if("undefined"!=typeof i&&i[q+"-"+r+"-"+s])return[!1,"not_bookable",booking_form_params.i18n_date_unavailable];if(""+q+r+s<wc_bookings_booking_form.current_time)return[!1,"not_bookable",booking_form_params.i18n_date_unavailable];if(j&&j[q+"-"+r+"-"+s]&&(j[q+"-"+r+"-"+s][0]||j[q+"-"+r+"-"+s][n])&&(l+="partial_booked "),d.find("#wc_bookings_field_duration").size()>0&&"minute"!=wc_bookings_booking_form.duration_unit&&"hour"!=wc_bookings_booking_form.duration_unit&&!e.data("is_range_picker_enabled"))var t=d.find("#wc_bookings_field_duration").val(),u=o*t;else var u=o;(1>u||"start"==k)&&(u=1);for(var v=g,w=0;u>w;w++){var p=new Date(c);p.setDate(p.getDate()+w);var q=p.getFullYear(),r=p.getMonth()+1,s=p.getDate(),x=p.getDay(),y=a.datepicker.iso8601Week(p);if(v=g,0==x&&(x=7),a.each(f[n],function(a,b){var c=b[0],d=b[1];try{switch(c){case"months":if("undefined"!=typeof d[r])return v=d[r],!1;break;case"weeks":if("undefined"!=typeof d[y])return v=d[y],!1;break;case"days":if("undefined"!=typeof d[x])return v=d[x],!1;break;case"custom":if("undefined"!=typeof d[q][r][s])return v=d[q][r][s],!1;break;case"time":case"time:1":case"time:2":case"time:3":case"time:4":case"time:5":case"time:6":case"time:7":if(x===d.day||0===d.day)return v=d.rule,!1;break;case"time:range":if("undefined"!=typeof d[q][r][s])return v=d[q][r][s].rule,!1}}catch(e){}return!0}),h[q+"-"+r+"-"+s]&&(h[q+"-"+r+"-"+s][0]||h[q+"-"+r+"-"+s][n])&&(v=!1),!v)break}if(v){if(m=l.indexOf("partial_booked")>-1?booking_form_params.i18n_date_partially_booked:booking_form_params.i18n_date_available,e.data("is_range_picker_enabled")){var z=a(this).closest("fieldset"),A=a.datepicker.parseDate(a.datepicker.ISO_8601,b.get_input_date(z,"")),B=a.datepicker.parseDate(a.datepicker.ISO_8601,b.get_input_date(z,"to_"));return[v,A&&(c.getTime()===A.getTime()||B&&c>=A&&B>=c)?l+"bookable-range":l+"bookable",m]}return[v,l+"bookable",m]}return[v,"not_bookable",booking_form_params.i18n_date_unavailable]}};b.init()});