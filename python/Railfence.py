def rail_fence_encrypt(text, rails):
    """Encrypts text using Rail Fence Cipher."""
    # Create a list of strings for each rail
    fence = [[] for _ in range(rails)]
    rail = 0
    direction = 1 # 1 = down, -1 = up
    
    for char in text:
        fence[rail].append(char)
        rail += direction
        
        # Bounce when hitting top or bottom rail
        if rail == rails:
            rail = rails - 2
            direction = -1
        elif rail < 0:
            rail = 1
            direction = 1
            
    # Join all rows to get ciphertext
    return "".join("".join(row) for row in fence)

def rail_fence_decrypt(text, rails):
    """Decrypts text using Rail Fence Cipher."""
    # 1. Reconstruct the zigzag pattern with placeholders
    fence = [['\n' for _ in range(len(text))] for _ in range(rails)]
    rail = 0
    direction = 1
    
    # Mark the spots with '*'
    for i in range(len(text)):
        fence[rail][i] = '*'
        rail += direction
        
        if rail == rails:
            rail = rails - 2
            direction = -1
        elif rail < 0:
            rail = 1
            direction = 1
            
    # 2. Fill the fence with the ciphertext
    index = 0
    for r in range(rails):
        for c in range(len(text)):
            if fence[r][c] == '*' and index < len(text):
                fence[r][c] = text[index]
                index += 1
                
    # 3. Read the matrix in zigzag to get plaintext
    result = []
    rail = 0
    direction = 1
    for i in range(len(text)):
        result.append(fence[rail][i])
        rail += direction
        
        if rail == rails:
            rail = rails - 2
            direction = -1
        elif rail < 0:
            rail = 1
            direction = 1
            
    return "".join(result)

# --- Main Execution ---
if __name__ == "__main__":
    text = "HELLO WORLD"
    rails = 2 # Depth
    
    encrypted = rail_fence_encrypt(text, rails)
    decrypted = rail_fence_decrypt(encrypted, rails)
    
    print(f"Original:  {text}")
    print(f"Encrypted: {encrypted}")
    print(f"Decrypted: {decrypted}")