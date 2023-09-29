import { Role } from "./role";

export interface User {
    username: string,
    role: Role,
    token: string
}