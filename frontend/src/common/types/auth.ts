export interface AuthorRegistrationRequest
{
    name: string,
    email: string,
    password: string,
    birthDate: Date,
    gender: Gender
}

export interface AuthorLoginRequest
{
    username: string
    password: string
}

export enum Gender {
    MALE = 'MALE',FEMALE = 'FEMALE' ,UNSPECIFIED = 'UNSPECIFIED'
}
export interface UserData {
    id: string,
    name: string,
    email: string,
    gender: Gender,
    birthDate: Date,
    username: string,
    imageIconId: string
}

export type ErrorMessage = string;