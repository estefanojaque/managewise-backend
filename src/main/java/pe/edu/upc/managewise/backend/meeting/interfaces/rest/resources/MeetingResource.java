package pe.edu.upc.managewise.backend.meeting.interfaces.rest.resources;

import pe.edu.upc.managewise.backend.meeting.interfaces.rest.resources.RecordingResource;

public record MeetingResource(
        Long id,
        String title,
        String dateStr,
        String timeStr,
        String link,
        String accessCode,
        RecordingResource recording
) {}
