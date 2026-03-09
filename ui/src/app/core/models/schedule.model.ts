export interface Schedule {
  scheduleId: number;
  courseClassId: number;
  courseClassLabel: string;
  roomId: number;
  roomCode: string;
  dayOfWeek: number;
  periodStart: number;
  periodEnd: number | null;
  note: string | null;
}

export interface ScheduleRequest {
  courseClassId: number;
  roomId: number;
  dayOfWeek: number;
  periodStart: number;
  periodEnd: number | null;
  note: string | null;
}
