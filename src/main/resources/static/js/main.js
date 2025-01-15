let eventsList = [];
document.addEventListener('DOMContentLoaded', function () {
    console.log("se carga el calendario")
    let calendarEl = document.getElementById('calendar');
    let eventsLoad = {};
    let calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        headerToolbar: {
            left: 'prev,next',
            center: 'title',
            right: 'dayGridMonth'
        },
        buttonText: {
            month: 'Mes'
        },
        dateClick: function(selectInfo) {
            console.log(selectInfo)
            if (calendar.view.type === 'dayGridMonth') {
                calendar.removeAllEvents();
                console.log("click en el mes")

                const url = `http://localhost:8080/api/v1/reservation/${selectInfo.dateStr}`;
                fetch(url)
                .then(response => response.json())
                .then(data => {
                    data.forEach(event => {
                        const { dayAvailable, hourAvailable } = event.reservationDate;
                        const combinedDate = new Date(`${dayAvailable}T${hourAvailable}`);
                        calendar.addEvent({
                            title: `Reserva: ${event.customer.firstName}-${event.customer.document.number}`,
                            start: combinedDate,
                            end: combinedDate
                        });
                        eventsList.push({
                            start: combinedDate,
                            end: combinedDate
                        });
                    });
                    console.log(eventsList)
                })
                .catch(error => console.info('Error al cargar eventos:', error));
            }
            calendar.changeView('timeGridDay', selectInfo.dateStr);
        },
        slotDuration: '01:00:00',
        selectable: true,
        businessHours: {
            daysOfWeek: [0, 1, 2, 3, 4, 5, 6],
            startTime: '11:00',
            endTime: '24:00'
        },
        selectConstraint: {
            startTime: '11:00',
            endTime: '24:00'
        },
        selectAllow: function(selectInfo) {
            console.log("hola")
            return calendar.view.type === 'timeGridDay';
        }, 
        select: function(info) {
            console.log("click en el dia")
            let canSelect = true;
            eventsList.forEach(event => {
                const selectedStart = info.start;
                if (selectedStart.getTime() === event.start.getTime()) {
                    canSelect = false; 
                }
            });
            if (canSelect) {
                console.log("Se puede agregar reservacion") 
                const eventModal = new bootstrap.Modal(document.getElementById('eventModalSchedule'));
                eventModal.show();

                document.getElementById('reserveButton').addEventListener('click', function() {
                    const customerName = document.getElementById('customer_name').value;
                    const customerLastName = document.getElementById('customer_last_name').value;
                    const customerDocument = document.getElementById('customer_document').value;
                    const customerPhone = document.getElementById('customer_tel').value;
                    const customerEmail = document.getElementById('customer_email').value;
                
                    if (!customerName || !customerLastName || !customerDocument) {
                        alert("Por favor complete todos los campos obligatorios.");
                        return; 
                    }
                
                    const day = info.startStr.split('T')[0]
                    const hour = info.start.getHours().toString().padStart(2, '0') + ":" + info.start.getMinutes().toString().padStart(2, '0');
                    console.log(day);
                    console.log(hour)
                    const url = `http://localhost:8080/api/v1/reservation`;
                    const requestData = {
                        reservationDate: {
                            dayAvailable: day,
                            hourAvailable: hour
                        },
                        customer: {
                            firstName: customerName,
                            lastName: customerLastName,
                            document: {
                                number: customerDocument,
                                type: "CC"
                            },
                            phoneNumber: customerPhone,
                            email: customerEmail
                        }
                    };

                    fetch(url, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(requestData)
                    })
                    .then(response => response.json())
                    .then(data => {
                        const { dayAvailable, hourAvailable } = data.reservationDate;
                        const combinedDate = new Date(`${dayAvailable}T${hourAvailable}`);
                        calendar.addEvent({
                            title: `Reserva: ${data.customer.firstName}-${data.customer.document.number}`,
                            start: combinedDate,
                            end: combinedDate
                        });
                        eventsList.push({
                            start: combinedDate,
                            end: combinedDate
                        });
                        console.log('Respuesta:', data);
                        eventModal.hide();
                        setForm();
                    })
                    .catch(error => {
                        console.log('Error: guardando', error);
                        eventModal.hide();
                        setForm();
                    });
                    setForm();
                });
            } else {
                console.log("No se puede agregar reservacion") 
            }
        },
        events: [],
        eventClick: function(info) {
            const { title, start, end } = info.event;
            let endDate = new Date();
            endDate.setTime(start.getTime() + (60 * 60 * 1000));

            document.getElementById('eventTitle').textContent = title;
            document.getElementById('eventStart').textContent = start.toLocaleString();
            document.getElementById('eventEnd').textContent = endDate.toLocaleString();
            const eventModal = new bootstrap.Modal(document.getElementById('eventModal'));
            eventModal.show();
        },
        views: {
            dayGridMonth: {
              eventDisplay: 'none'
            },
            timeGridDay: {
              eventDisplay: 'block'
            }
        },
        datesSet: function (info) {
            if (info.view.type === 'dayGridMonth') {
                calendar.removeAllEvents();
            }
        }
    });
    calendar.render();
    document.getElementById('dialog').addEventListener('shown.bs.modal', function () {
        calendar.updateSize();
    });
});

function setForm() {
    document.getElementById('customer_name').value = "";
    document.getElementById('customer_last_name').value = "";
    document.getElementById('customer_document').value = "";
    document.getElementById('customer_tel').value = "";
    document.getElementById('customer_email').value = "";       
}