export interface Room {
  roomId: number;
  roomCode: string;
  building: string | null;
  capacity: number | null;
  roomType: string | null;
  description: string | null;
}

export interface RoomRequest {
  roomCode: string;
  building: string | null;
  capacity: number | null;
  roomType: string | null;
  description: string | null;
}
