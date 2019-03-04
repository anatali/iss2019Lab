package it.unibo.bls19d;

import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import it.unibo.contactEvent.interfaces.IActorMessage;


public class QActorMessage implements IActorMessage {
//msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM )
	String msgId  ;
	String msgType  ; 
	String msgSender  ;
	String msgReceiver  ;
	String msgContent  ;
	int msgNum  ;
	
	public QActorMessage(
			String MSGID, String MSGTYPE, String SENDER, String RECEIVER, String CONTENT, String SEQNUM) throws Exception{
		msgId       = MSGID;
		msgType     = MSGTYPE;
		msgSender   = SENDER;
		msgReceiver = RECEIVER;
		msgContent  = envelope(CONTENT);
		msgNum      = Integer.parseInt( SEQNUM );
//		System.out.println("QActorMessage " + MSGID + " " + getDefaultRep() );
	}
	
	public QActorMessage(String msg) throws Exception{
		Struct msgStruct = (Struct) Term.createTerm(msg);
		setFields(msgStruct);
 	}
	
	private void setFields(Struct msgStruct){
		msgId		= 		msgStruct.getArg(0).toString();
		msgType		=		msgStruct.getArg(1).toString();
		msgSender	=		msgStruct.getArg(2).toString();
		msgReceiver	=		msgStruct.getArg(3).toString();
		msgContent	=		msgStruct.getArg(4).toString();
 		msgNum      =		Integer.parseInt(msgStruct.getArg(5).toString());
 	}
	@Override
	public String msgId() {
 		return msgId;
	}
	@Override
	public String msgType() {
 		return msgType;
	}
	@Override
 	public String msgSender() {
 		return msgSender;
	}
	@Override
 	public String msgReceiver() {
 		return msgReceiver;
	}
	@Override
	public String msgContent() {
 		return msgContent;
	}
	@Override
	public String msgNum() {
 		return ""+msgNum;
	}
	@Override
	public String toString() {
 		return getDefaultRep();
	}

	@Override
	public String getDefaultRep() {
  		if( msgType == null ) return "msg(none,none,none,none,none,0)";
  		else return "msg(MSGID,MSGTYPE,MSGSENDER,MSGRECEIVER,MSGCONTENT,MSGNUM)".replace("MSGID", msgId).
  				replace("MSGTYPE", msgType).replace("MSGSENDER", msgSender).
 				replace("MSGRECEIVER", msgReceiver).replace("MSGCONTENT", msgContent).replace("MSGNUM", msgNum() );
	}
	
	public Term getTerm() {
		if( msgType == null ) return Term.createTerm("msg(none,none,none,none,none,0)");
 		else return Term.createTerm(msgContent);
	}
	
	protected String envelope( String content){
		try{
			Term tt = Term.createTerm(content);
			return tt.toString();
		}catch(Exception e){
			return "'"+content+"'";
		}
	}

}
