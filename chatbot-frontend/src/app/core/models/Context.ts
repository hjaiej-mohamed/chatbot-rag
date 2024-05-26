export interface Context{
    id:number;
    pdfName:string;
    description:string;
    pdf:ArrayBuffer;
    creationDate:Date;
    updateDate:Date;
}