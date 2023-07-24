package com.chernenkov.webservlet.entity;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;

public class Medicine extends AbstractEntity {
    private int id;
    private String name;
    private BigDecimal price;
    boolean withoutRecipe;
    private String description;

    private Medicine(Medicine.Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.price = builder.price;
        this.withoutRecipe = builder.withoutRecipe;
        this.description = builder.description;
    }

    public static class Builder {
        private int id;
        private String name;
        private BigDecimal price;
        private boolean withoutRecipe;
        private String description;


        public Builder() {
        }

        public Medicine.Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Medicine.Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Medicine.Builder setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Medicine.Builder setWithoutRecipe(int withoutRecipeInt) {
            if (withoutRecipeInt == 1) {
                this.withoutRecipe = true;
            } else {
                this.withoutRecipe = false;
            }
            return this;
        }
        public Medicine.Builder setWithoutRecipe(boolean withoutRecipe) {
            this.withoutRecipe = withoutRecipe;
            return this;
        }


        public Medicine.Builder setDescription(String description) {
            this.description = description;
            return this;
        }


        public Medicine build() {
            return new Medicine(this);
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isWithoutRecipe() {
        return withoutRecipe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicine medicine = (Medicine) o;
        return id == medicine.id && withoutRecipe == medicine.withoutRecipe && Objects.equals(name, medicine.name) && Objects.equals(price, medicine.price) && Objects.equals(description, medicine.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, withoutRecipe, description);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Medicine{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append(", withoutRecipe=").append(withoutRecipe);
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
