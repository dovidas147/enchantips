package com.fedpol1.enchantips.config.tree;

import com.fedpol1.enchantips.config.data.AbstractDataEntry;
import com.fedpol1.enchantips.config.data.Data;
import com.fedpol1.enchantips.config.data.visitor.OptionVisitor;
import com.fedpol1.enchantips.config.tree.visitor.ScreenVisitor;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import net.minecraft.text.Text;

public class OptionNode<T> extends AbstractNode{

    private final AbstractDataEntry<T> entry;

    public OptionNode(AbstractDataEntry<T> entry) {
        super(entry.getKey());
        this.entry = entry;
    }

    public Data<T> getData() {
        return this.entry.getData();
    }

    public T getValue() {
        return this.entry.getData().getValue();
    }

    public Option<?> getYaclOption() {
        OptionDescription.Builder desc = OptionDescription.createBuilder();
        for(int i = 0; i < this.entry.getNumTooltipLines(); i++) {
            desc = desc.text(Text.translatable(this.getFullName() + ".option_tooltip." + i));
        }

        Option.Builder<?> b = (Option.Builder<?>) this.entry.getData().accept(new OptionVisitor());
        return b.name(Text.translatable(this.getFullName() + ".option_title"))
                .description(desc.build())
                .build();
    }

    public Object accept(ScreenVisitor v, Object data) {
        return v.visit(this, data);
    }
}
