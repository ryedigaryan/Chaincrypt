package rub.labs.chaining.block;

import lombok.NoArgsConstructor;
import rub.labs.chaining.utils.Modifier;
import rub.labs.chaining.utils.Utils;
import rub.labs.cryptoalgo.EncryptionAlgorithm;

@NoArgsConstructor
public class CFB<K> extends EncryptorBlockCrypter<K> {

    private byte[] lastVector;

    private class EncryptionModifier implements Modifier {

        private byte[] plainText;

        @Override
        public byte[] firstModification(byte[] data, byte[] vector, int inputLength) {
            plainText = data;
            return vector;
        }

        @Override
        public byte[] secondModification(byte[] data, byte[] vector, int inputLength) {
            return lastVector = Utils.xor(data, plainText);
        }
    }

    private class DecryptionModifier implements Modifier {

        private byte[] cipherText;

        @Override
        public byte[] firstModification(byte[] data, byte[] vector, int inputLength) {
            cipherText = data;
            return vector;
        }

        @Override
        public byte[] secondModification(byte[] data, byte[] vector, int inputLength) {
            return Utils.xor(data, lastVector = cipherText);
        }
    }

    private final EncryptionModifier eMod = new EncryptionModifier();
    private final DecryptionModifier dMod = new DecryptionModifier();

    public CFB(EncryptionAlgorithm<K> rootAlgorithm) {
        super(rootAlgorithm);
    }

    @Override
    protected Modifier encryptionModifier() {
        return eMod;
    }

    @Override
    protected Modifier decryptionModifier() {
        return dMod;
    }

    @Override
    public byte[] getLastGeneratedVector() {
        return lastVector;
    }
}
