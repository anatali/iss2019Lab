/*
 * generated by Xtext 2.16.0
 */
package it.unibo.xtext.intro19.ide.contentassist.antlr;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import it.unibo.xtext.intro19.ide.contentassist.antlr.internal.InternalIotParser;
import it.unibo.xtext.intro19.services.IotGrammarAccess;
import java.util.Map;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.AbstractContentAssistParser;

public class IotParser extends AbstractContentAssistParser {

	@Singleton
	public static final class NameMappings {
		
		private final Map<AbstractElement, String> mappings;
		
		@Inject
		public NameMappings(IotGrammarAccess grammarAccess) {
			ImmutableMap.Builder<AbstractElement, String> builder = ImmutableMap.builder();
			init(builder, grammarAccess);
			this.mappings = builder.build();
		}
		
		public String getRuleName(AbstractElement element) {
			return mappings.get(element);
		}
		
		private static void init(ImmutableMap.Builder<AbstractElement, String> builder, IotGrammarAccess grammarAccess) {
			builder.put(grammarAccess.getMessageAccess().getAlternatives(), "rule__Message__Alternatives");
			builder.put(grammarAccess.getIotSystemAccess().getGroup(), "rule__IotSystem__Group__0");
			builder.put(grammarAccess.getQualifiedNameAccess().getGroup(), "rule__QualifiedName__Group__0");
			builder.put(grammarAccess.getQualifiedNameAccess().getGroup_1(), "rule__QualifiedName__Group_1__0");
			builder.put(grammarAccess.getIotSystemSpecAccess().getGroup(), "rule__IotSystemSpec__Group__0");
			builder.put(grammarAccess.getBrokerSpecAccess().getGroup(), "rule__BrokerSpec__Group__0");
			builder.put(grammarAccess.getEventAccess().getGroup(), "rule__Event__Group__0");
			builder.put(grammarAccess.getDispatchAccess().getGroup(), "rule__Dispatch__Group__0");
			builder.put(grammarAccess.getIotSystemAccess().getSpecAssignment_1(), "rule__IotSystem__SpecAssignment_1");
			builder.put(grammarAccess.getIotSystemSpecAccess().getNameAssignment_0(), "rule__IotSystemSpec__NameAssignment_0");
			builder.put(grammarAccess.getIotSystemSpecAccess().getMqttBrokerAssignment_1(), "rule__IotSystemSpec__MqttBrokerAssignment_1");
			builder.put(grammarAccess.getIotSystemSpecAccess().getMessageAssignment_2(), "rule__IotSystemSpec__MessageAssignment_2");
			builder.put(grammarAccess.getBrokerSpecAccess().getBrokerHostAssignment_1(), "rule__BrokerSpec__BrokerHostAssignment_1");
			builder.put(grammarAccess.getBrokerSpecAccess().getBrokerPortAssignment_3(), "rule__BrokerSpec__BrokerPortAssignment_3");
			builder.put(grammarAccess.getEventAccess().getNameAssignment_1(), "rule__Event__NameAssignment_1");
			builder.put(grammarAccess.getEventAccess().getMsgAssignment_3(), "rule__Event__MsgAssignment_3");
			builder.put(grammarAccess.getDispatchAccess().getNameAssignment_1(), "rule__Dispatch__NameAssignment_1");
			builder.put(grammarAccess.getDispatchAccess().getMsgAssignment_3(), "rule__Dispatch__MsgAssignment_3");
		}
	}
	
	@Inject
	private NameMappings nameMappings;

	@Inject
	private IotGrammarAccess grammarAccess;

	@Override
	protected InternalIotParser createParser() {
		InternalIotParser result = new InternalIotParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}

	@Override
	protected String getRuleName(AbstractElement element) {
		return nameMappings.getRuleName(element);
	}

	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}

	public IotGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(IotGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
	public NameMappings getNameMappings() {
		return nameMappings;
	}
	
	public void setNameMappings(NameMappings nameMappings) {
		this.nameMappings = nameMappings;
	}
}
