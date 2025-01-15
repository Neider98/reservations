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
                            end: combinedDate,
                            extendedProps: {
                                reservationId: event.id
                            }
                        });
                        eventsList.push({
                            start: combinedDate,
                            end: combinedDate,
                            reservationId: event.id
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
            return calendar.view.type === 'timeGridDay';
        }, 
        select: function(info) {
            let canSelect = true;
            eventsList.forEach(event => {
                const selectedStart = info.start;
                if (selectedStart.getTime() === event.start.getTime()) {
                    canSelect = false; 
                }
            });
            if (canSelect) {
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
                        },
                        status: "CREADA"
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
                            end: combinedDate,
                            extendedProps: {
                                reservationId: data.id
                            }
                        });
                        eventsList.push({
                            start: combinedDate,
                            end: combinedDate,
                            reservationId: data.id
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
            const { title, start, end, extendedProps } = info.event;
            currentReservation = info.event;
            let endDate = new Date();
            endDate.setTime(start.getTime() + (60 * 60 * 1000));

            document.getElementById('eventTitle').textContent = title;
            document.getElementById('eventStart').textContent = start.toLocaleString();
            document.getElementById('eventEnd').textContent = endDate.toLocaleString();

            document.getElementById('edit_customer_name').value = extendedProps.firstName || '';
            document.getElementById('edit_customer_last_name').value = extendedProps.lastName || '';
            document.getElementById('edit_customer_document').value = extendedProps.documentNumber || '';
            document.getElementById('edit_customer_tel').value = extendedProps.phoneNumber || '';
            document.getElementById('edit_customer_email').value = extendedProps.email || '';
            document.getElementById('edit_date').value = start.toISOString().split('T')[0];
            document.getElementById('edit_time').value = start.toTimeString().slice(0, 5);

            showDetailsView();
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

let currentReservation = null;
let isEditMode = false;

document.getElementById('deleteReservation').addEventListener('click', function() {
    if (!currentReservation) return;

    if (confirm('¿Está seguro que desea eliminar esta reservación?')) {
        const reservationId = currentReservation.extendedProps.reservationId;
        
        fetch(`http://localhost:8080/api/v1/reservation/${reservationId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (response.ok) {
                currentReservation.remove();
                
                eventsList = eventsList.filter(event => event.reservationId !== reservationId);
                
                const eventModal = bootstrap.Modal.getInstance(document.getElementById('eventModal'));
                eventModal.hide();
                
                currentReservation = null;
            } else {
                throw new Error('Error al eliminar la reservación');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('No se pudo eliminar la reservación');
        });
    }
});

document.getElementById('editReservation').addEventListener('click', function() {
    showEditView();
});

document.getElementById('cancelEdit').addEventListener('click', function() {
    showDetailsView();
});

document.getElementById('saveEdit').addEventListener('click', function() {
    const reservationId = currentReservation.extendedProps.reservationId;
    const newDate = document.getElementById('edit_date').value;
    const newTime = document.getElementById('edit_time').value;

    const updatedData = {
        reservationDate: {
            dayAvailable: newDate,
            hourAvailable: newTime
        },
        customer: {
            firstName: document.getElementById('edit_customer_name').value,
            lastName: document.getElementById('edit_customer_last_name').value,
            document: {
                number: document.getElementById('edit_customer_document').value,
                type: "CC"
            },
            phoneNumber: document.getElementById('edit_customer_tel').value,
            email: document.getElementById('edit_customer_email').value
        },
        status: "CREADA"
    };

    fetch(`http://localhost:8080/api/v1/reservation/${reservationId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedData)
    })
    .then(response => response.json())
    .then(data => {
        const combinedDate = new Date(`${data.reservationDate.dayAvailable}T${data.reservationDate.hourAvailable}`);
        
        currentReservation.setProp('title', `Reserva: ${data.customer.firstName}-${data.customer.document.number}`);
        currentReservation.setStart(combinedDate);
        currentReservation.setEnd(combinedDate);
        
        const eventIndex = eventsList.findIndex(e => e.reservationId === reservationId);
        if (eventIndex !== -1) {
            eventsList[eventIndex] = {
                start: combinedDate,
                end: combinedDate,
                reservationId: reservationId
            };
        }

        const eventModal = bootstrap.Modal.getInstance(document.getElementById('eventModal'));
        eventModal.hide();
    })
    .catch(error => {
        console.error('Error:', error);
        alert('No se pudo actualizar la reservación');
    });
});

function showEditView() {
    document.getElementById('detailsView').style.display = 'none';
    document.getElementById('editView').style.display = 'block';
    document.getElementById('editReservation').style.display = 'none';
    document.getElementById('deleteReservation').style.display = 'none';
    document.getElementById('cancelEdit').style.display = 'inline-block';
    document.getElementById('saveEdit').style.display = 'inline-block';
    isEditMode = true;
}

function showDetailsView() {
    document.getElementById('detailsView').style.display = 'block';
    document.getElementById('editView').style.display = 'none';
    document.getElementById('editReservation').style.display = 'inline-block';
    document.getElementById('deleteReservation').style.display = 'inline-block';
    document.getElementById('cancelEdit').style.display = 'none';
    document.getElementById('saveEdit').style.display = 'none';
    isEditMode = false;
}