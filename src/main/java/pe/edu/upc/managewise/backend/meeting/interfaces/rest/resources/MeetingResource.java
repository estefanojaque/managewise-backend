package pe.edu.upc.managewise.backend.meeting.interfaces.rest.resources;

import pe.edu.upc.managewise.backend.meeting.interfaces.rest.resources.RecordingResource;

import java.util.List;

public record MeetingResource(
        Long id,
        String title,
        String dateStr,
        String timeStr,
        String link,
        String accessCode,
        RecordingResource recording,
        Long host,
        List<Long> members
) {}
