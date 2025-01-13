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
        dateClick: function(info) {
            calendar.changeView('timeGridDay', info.dateStr);
        },
        slotDuration: '01:00:00',
        selectable: true,
        selectAllow: function(selectInfo) {
            if (calendar.view.type === 'dayGridMonth') {
                const date = selectInfo.start;
                const formattedDate = date.toISOString().split('T')[0]; 
                calendar.removeAllEvents();
                console.log("click en el mes")

                const url = `http://localhost:8080/api/v1/reservation/${formattedDate}`;
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
                    });
                })
                .catch(error => console.info('Error al cargar eventos:', error));
            }
            return calendar.view.type === 'timeGridDay';
        }, 
        select: function(info) {
            console.log("click en el dia")
        },
        events: [],
        eventClick: function(info) {
            const { title, start, end } = info.event;
            console.log(info)
            console.log(info.event)
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
                calendar.removeAllEvents(); // Borra eventos al cambiar a la vista de mes
            }
        }
    });
    calendar.render();
    document.getElementById('dialog').addEventListener('shown.bs.modal', function () {
        calendar.updateSize();
    });
});